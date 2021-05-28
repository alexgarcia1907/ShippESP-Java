/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Calles;
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
public class CallesJpaController implements Serializable {

 public CallesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProjecteDawPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calles calles) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(calles);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalles(calles.getId()) != null) {
                throw new PreexistingEntityException("Calles " + calles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calles calles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            calles = em.merge(calles);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = calles.getId();
                if (findCalles(id) == null) {
                    throw new NonexistentEntityException("The calles with id " + id + " no longer exists.");
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
            Calles calles;
            try {
                calles = em.getReference(Calles.class, id);
                calles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calles with id " + id + " no longer exists.", enfe);
            }
            em.remove(calles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calles> findCallesEntities() {
        return findCallesEntities(true, -1, -1);
    }

    public List<Calles> findCallesEntities(int maxResults, int firstResult) {
        return findCallesEntities(false, maxResults, firstResult);
    }

    private List<Calles> findCallesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calles.class));
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

    public Calles findCalles(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calles.class, id);
        } finally {
            em.close();
        }
    }

    public int getCallesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calles> rt = cq.from(Calles.class);
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
            CriteriaDelete<Calles> criteriaDelete = criteriaBuilder.createCriteriaDelete(Calles.class);
            criteriaDelete.from(Calles.class);
            em.getTransaction().begin(); 
            int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
            System.out.println("entities deleted: " + rowsDeleted);
            em.getTransaction().commit();
            em.close();
    }
    
}
