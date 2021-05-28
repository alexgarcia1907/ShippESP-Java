/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Cpostales;
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
public class CpostalesJpaController implements Serializable {

 public CpostalesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProjecteDawPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cpostales cpostales) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cpostales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCpostales(cpostales.getId()) != null) {
                throw new PreexistingEntityException("Cpostales " + cpostales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cpostales cpostales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cpostales = em.merge(cpostales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cpostales.getId();
                if (findCpostales(id) == null) {
                    throw new NonexistentEntityException("The cpostales with id " + id + " no longer exists.");
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
            Cpostales cpostales;
            try {
                cpostales = em.getReference(Cpostales.class, id);
                cpostales.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cpostales with id " + id + " no longer exists.", enfe);
            }
            em.remove(cpostales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cpostales> findCpostalesEntities() {
        return findCpostalesEntities(true, -1, -1);
    }

    public List<Cpostales> findCpostalesEntities(int maxResults, int firstResult) {
        return findCpostalesEntities(false, maxResults, firstResult);
    }

    private List<Cpostales> findCpostalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cpostales.class));
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

    public Cpostales findCpostales(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cpostales.class, id);
        } finally {
            em.close();
        }
    }

    public int getCpostalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cpostales> rt = cq.from(Cpostales.class);
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
            CriteriaDelete<Cpostales> criteriaDelete = criteriaBuilder.createCriteriaDelete(Cpostales.class);
            criteriaDelete.from(Cpostales.class);
            em.getTransaction().begin(); 
            int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
            System.out.println("entities deleted: " + rowsDeleted);
            em.getTransaction().commit();
            em.close();
    }
    
}
