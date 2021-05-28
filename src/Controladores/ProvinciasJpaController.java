/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Provincias;
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
public class ProvinciasJpaController implements Serializable {

 public ProvinciasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProjecteDawPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincias provincias) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(provincias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvincias(provincias.getCpro()) != null) {
                throw new PreexistingEntityException("Provincias " + provincias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincias provincias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            provincias = em.merge(provincias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = provincias.getCpro();
                if (findProvincias(id) == null) {
                    throw new NonexistentEntityException("The provincias with id " + id + " no longer exists.");
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
            Provincias provincias;
            try {
                provincias = em.getReference(Provincias.class, id);
                provincias.getCpro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincias with id " + id + " no longer exists.", enfe);
            }
            em.remove(provincias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincias> findProvinciasEntities() {
        return findProvinciasEntities(true, -1, -1);
    }

    public List<Provincias> findProvinciasEntities(int maxResults, int firstResult) {
        return findProvinciasEntities(false, maxResults, firstResult);
    }

    private List<Provincias> findProvinciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincias.class));
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

    public Provincias findProvincias(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincias.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincias> rt = cq.from(Provincias.class);
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
            CriteriaDelete<Provincias> criteriaDelete = criteriaBuilder.createCriteriaDelete(Provincias.class);
            criteriaDelete.from(Provincias.class);
            em.getTransaction().begin(); 
            int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
            System.out.println("entities deleted: " + rowsDeleted);
            em.getTransaction().commit();
            em.close();
    }
    
}
