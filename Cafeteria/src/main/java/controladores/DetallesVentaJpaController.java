/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.DetallesVenta;
import modelos.Ventas;

/**
 *
 * @author Edgar
 */
public class DetallesVentaJpaController implements Serializable {

    public DetallesVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallesVenta detallesVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas idVenta = detallesVenta.getIdVenta();
            if (idVenta != null) {
                idVenta = em.getReference(idVenta.getClass(), idVenta.getIdVenta());
                detallesVenta.setIdVenta(idVenta);
            }
            em.persist(detallesVenta);
            if (idVenta != null) {
                idVenta.getDetallesVentaCollection().add(detallesVenta);
                idVenta = em.merge(idVenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallesVenta detallesVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallesVenta persistentDetallesVenta = em.find(DetallesVenta.class, detallesVenta.getIdDetalle());
            Ventas idVentaOld = persistentDetallesVenta.getIdVenta();
            Ventas idVentaNew = detallesVenta.getIdVenta();
            if (idVentaNew != null) {
                idVentaNew = em.getReference(idVentaNew.getClass(), idVentaNew.getIdVenta());
                detallesVenta.setIdVenta(idVentaNew);
            }
            detallesVenta = em.merge(detallesVenta);
            if (idVentaOld != null && !idVentaOld.equals(idVentaNew)) {
                idVentaOld.getDetallesVentaCollection().remove(detallesVenta);
                idVentaOld = em.merge(idVentaOld);
            }
            if (idVentaNew != null && !idVentaNew.equals(idVentaOld)) {
                idVentaNew.getDetallesVentaCollection().add(detallesVenta);
                idVentaNew = em.merge(idVentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallesVenta.getIdDetalle();
                if (findDetallesVenta(id) == null) {
                    throw new NonexistentEntityException("The detallesVenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallesVenta detallesVenta;
            try {
                detallesVenta = em.getReference(DetallesVenta.class, id);
                detallesVenta.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallesVenta with id " + id + " no longer exists.", enfe);
            }
            Ventas idVenta = detallesVenta.getIdVenta();
            if (idVenta != null) {
                idVenta.getDetallesVentaCollection().remove(detallesVenta);
                idVenta = em.merge(idVenta);
            }
            em.remove(detallesVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallesVenta> findDetallesVentaEntities() {
        return findDetallesVentaEntities(true, -1, -1);
    }

    public List<DetallesVenta> findDetallesVentaEntities(int maxResults, int firstResult) {
        return findDetallesVentaEntities(false, maxResults, firstResult);
    }

    private List<DetallesVenta> findDetallesVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallesVenta.class));
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

    public DetallesVenta findDetallesVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallesVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallesVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallesVenta> rt = cq.from(DetallesVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
