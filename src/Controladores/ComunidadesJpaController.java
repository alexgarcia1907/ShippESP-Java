/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Comunidades;
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
public class ComunidadesJpaController implements Serializable {

 public ComunidadesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProjecteDawPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comunidades comunidades) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comunidades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComunidades(comunidades.getCcom()) != null) {
                throw new PreexistingEntityException("Comunidades " + comunidades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comunidades comunidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comunidades = em.merge(comunidades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comunidades.getCcom();
                if (findComunidades(id) == null) {
                    throw new NonexistentEntityException("The comunidades with id " + id + " no longer exists.");
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
            Comunidades comunidades;
            try {
                comunidades = em.getReference(Comunidades.class, id);
                comunidades.getCcom();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunidades with id " + id + " no longer exists.", enfe);
            }
            em.remove(comunidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comunidades> findComunidadesEntities() {
        return findComunidadesEntities(true, -1, -1);
    }

    public List<Comunidades> findComunidadesEntities(int maxResults, int firstResult) {
        return findComunidadesEntities(false, maxResults, firstResult);
    }

    private List<Comunidades> findComunidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comunidades.class));
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

    public Comunidades findComunidades(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comunidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comunidades> rt = cq.from(Comunidades.class);
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
            CriteriaDelete<Comunidades> criteriaDelete = criteriaBuilder.createCriteriaDelete(Comunidades.class);
            criteriaDelete.from(Comunidades.class);
            em.getTransaction().begin(); 
            int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
            System.out.println("entities deleted: " + rowsDeleted);
            em.getTransaction().commit();
            em.close();
    }
    
}
