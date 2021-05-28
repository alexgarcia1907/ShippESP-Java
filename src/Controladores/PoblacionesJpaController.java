/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Poblaciones;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Álex García
 */
public class PoblacionesJpaController implements Serializable {

 public PoblacionesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProjecteDawPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Poblaciones poblaciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(poblaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPoblaciones(poblaciones.getCpob()) != null) {
                throw new PreexistingEntityException("Poblaciones " + poblaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Poblaciones poblaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            poblaciones = em.merge(poblaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = poblaciones.getCpob();
                if (findPoblaciones(id) == null) {
                    throw new NonexistentEntityException("The poblaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblaciones poblaciones;
            try {
                poblaciones = em.getReference(Poblaciones.class, id);
                poblaciones.getCpob();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poblaciones with id " + id + " no longer exists.", enfe);
            }
            em.remove(poblaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Poblaciones> findPoblacionesEntities() {
        return findPoblacionesEntities(true, -1, -1);
    }

    public List<Poblaciones> findPoblacionesEntities(int maxResults, int firstResult) {
        return findPoblacionesEntities(false, maxResults, firstResult);
    }

    private List<Poblaciones> findPoblacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Poblaciones.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Poblaciones findPoblaciones(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poblaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPoblacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Poblaciones> rt = cq.from(Poblaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public void deletetable() {
            EntityManager em = getEntityManager();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<Poblaciones> criteriaDelete = criteriaBuilder.createCriteriaDelete(Poblaciones.class);
            criteriaDelete.from(Poblaciones.class);
            em.getTransaction().begin(); 
            int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
            System.out.println("entities deleted: " + rowsDeleted);
            em.getTransaction().commit();
            em.close();
    }
    
}
