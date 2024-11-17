/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Usuario;
import modelos.DetallesVenta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelos.Ventas;

/**
 *
 * @author Edgar
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        if (ventas.getDetallesVentaCollection() == null) {
            ventas.setDetallesVentaCollection(new ArrayList<DetallesVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = ventas.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                ventas.setIdUsuario(idUsuario);
            }
            Collection<DetallesVenta> attachedDetallesVentaCollection = new ArrayList<DetallesVenta>();
            for (DetallesVenta detallesVentaCollectionDetallesVentaToAttach : ventas.getDetallesVentaCollection()) {
                detallesVentaCollectionDetallesVentaToAttach = em.getReference(detallesVentaCollectionDetallesVentaToAttach.getClass(), detallesVentaCollectionDetallesVentaToAttach.getIdDetalle());
                attachedDetallesVentaCollection.add(detallesVentaCollectionDetallesVentaToAttach);
            }
            ventas.setDetallesVentaCollection(attachedDetallesVentaCollection);
            em.persist(ventas);
            if (idUsuario != null) {
                idUsuario.getVentasCollection().add(ventas);
                idUsuario = em.merge(idUsuario);
            }
            for (DetallesVenta detallesVentaCollectionDetallesVenta : ventas.getDetallesVentaCollection()) {
                Ventas oldIdVentaOfDetallesVentaCollectionDetallesVenta = detallesVentaCollectionDetallesVenta.getIdVenta();
                detallesVentaCollectionDetallesVenta.setIdVenta(ventas);
                detallesVentaCollectionDetallesVenta = em.merge(detallesVentaCollectionDetallesVenta);
                if (oldIdVentaOfDetallesVentaCollectionDetallesVenta != null) {
                    oldIdVentaOfDetallesVentaCollectionDetallesVenta.getDetallesVentaCollection().remove(detallesVentaCollectionDetallesVenta);
                    oldIdVentaOfDetallesVentaCollectionDetallesVenta = em.merge(oldIdVentaOfDetallesVentaCollectionDetallesVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdVenta());
            Usuario idUsuarioOld = persistentVentas.getIdUsuario();
            Usuario idUsuarioNew = ventas.getIdUsuario();
            Collection<DetallesVenta> detallesVentaCollectionOld = persistentVentas.getDetallesVentaCollection();
            Collection<DetallesVenta> detallesVentaCollectionNew = ventas.getDetallesVentaCollection();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                ventas.setIdUsuario(idUsuarioNew);
            }
            Collection<DetallesVenta> attachedDetallesVentaCollectionNew = new ArrayList<DetallesVenta>();
            for (DetallesVenta detallesVentaCollectionNewDetallesVentaToAttach : detallesVentaCollectionNew) {
                detallesVentaCollectionNewDetallesVentaToAttach = em.getReference(detallesVentaCollectionNewDetallesVentaToAttach.getClass(), detallesVentaCollectionNewDetallesVentaToAttach.getIdDetalle());
                attachedDetallesVentaCollectionNew.add(detallesVentaCollectionNewDetallesVentaToAttach);
            }
            detallesVentaCollectionNew = attachedDetallesVentaCollectionNew;
            ventas.setDetallesVentaCollection(detallesVentaCollectionNew);
            ventas = em.merge(ventas);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getVentasCollection().remove(ventas);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getVentasCollection().add(ventas);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (DetallesVenta detallesVentaCollectionOldDetallesVenta : detallesVentaCollectionOld) {
                if (!detallesVentaCollectionNew.contains(detallesVentaCollectionOldDetallesVenta)) {
                    detallesVentaCollectionOldDetallesVenta.setIdVenta(null);
                    detallesVentaCollectionOldDetallesVenta = em.merge(detallesVentaCollectionOldDetallesVenta);
                }
            }
            for (DetallesVenta detallesVentaCollectionNewDetallesVenta : detallesVentaCollectionNew) {
                if (!detallesVentaCollectionOld.contains(detallesVentaCollectionNewDetallesVenta)) {
                    Ventas oldIdVentaOfDetallesVentaCollectionNewDetallesVenta = detallesVentaCollectionNewDetallesVenta.getIdVenta();
                    detallesVentaCollectionNewDetallesVenta.setIdVenta(ventas);
                    detallesVentaCollectionNewDetallesVenta = em.merge(detallesVentaCollectionNewDetallesVenta);
                    if (oldIdVentaOfDetallesVentaCollectionNewDetallesVenta != null && !oldIdVentaOfDetallesVentaCollectionNewDetallesVenta.equals(ventas)) {
                        oldIdVentaOfDetallesVentaCollectionNewDetallesVenta.getDetallesVentaCollection().remove(detallesVentaCollectionNewDetallesVenta);
                        oldIdVentaOfDetallesVentaCollectionNewDetallesVenta = em.merge(oldIdVentaOfDetallesVentaCollectionNewDetallesVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getIdVenta();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = ventas.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getVentasCollection().remove(ventas);
                idUsuario = em.merge(idUsuario);
            }
            Collection<DetallesVenta> detallesVentaCollection = ventas.getDetallesVentaCollection();
            for (DetallesVenta detallesVentaCollectionDetallesVenta : detallesVentaCollection) {
                detallesVentaCollectionDetallesVenta.setIdVenta(null);
                detallesVentaCollectionDetallesVenta = em.merge(detallesVentaCollectionDetallesVenta);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
