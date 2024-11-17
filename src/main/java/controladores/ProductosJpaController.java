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
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Categorias;
import modelos.Productos;
import modelos.Usuario;

/**
 *
 * @author Edgar
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProductosJpaController() {
        this.emf=Persistence.createEntityManagerFactory("PU");
    }
    
    

    public void create(Productos productos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias IDCategoria = productos.getIDCategoria();
            if (IDCategoria != null) {
                IDCategoria = em.getReference(IDCategoria.getClass(), IDCategoria.getIdCategoria());
                productos.setIDCategoria(IDCategoria);
            }
            Usuario IDUsuario = productos.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario = em.getReference(IDUsuario.getClass(), IDUsuario.getId());
                productos.setIDUsuario(IDUsuario);
            }
            em.persist(productos);
            if (IDCategoria != null) {
                IDCategoria.getProductosCollection().add(productos);
                IDCategoria = em.merge(IDCategoria);
            }
            if (IDUsuario != null) {
                IDUsuario.getProductosCollection().add(productos);
                IDUsuario = em.merge(IDUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getIDProducto());
            Categorias IDCategoriaOld = persistentProductos.getIDCategoria();
            Categorias IDCategoriaNew = productos.getIDCategoria();
            Usuario IDUsuarioOld = persistentProductos.getIDUsuario();
            Usuario IDUsuarioNew = productos.getIDUsuario();
            if (IDCategoriaNew != null) {
                IDCategoriaNew = em.getReference(IDCategoriaNew.getClass(), IDCategoriaNew.getIdCategoria());
                productos.setIDCategoria(IDCategoriaNew);
            }
            if (IDUsuarioNew != null) {
                IDUsuarioNew = em.getReference(IDUsuarioNew.getClass(), IDUsuarioNew.getId());
                productos.setIDUsuario(IDUsuarioNew);
            }
            productos = em.merge(productos);
            if (IDCategoriaOld != null && !IDCategoriaOld.equals(IDCategoriaNew)) {
                IDCategoriaOld.getProductosCollection().remove(productos);
                IDCategoriaOld = em.merge(IDCategoriaOld);
            }
            if (IDCategoriaNew != null && !IDCategoriaNew.equals(IDCategoriaOld)) {
                IDCategoriaNew.getProductosCollection().add(productos);
                IDCategoriaNew = em.merge(IDCategoriaNew);
            }
            if (IDUsuarioOld != null && !IDUsuarioOld.equals(IDUsuarioNew)) {
                IDUsuarioOld.getProductosCollection().remove(productos);
                IDUsuarioOld = em.merge(IDUsuarioOld);
            }
            if (IDUsuarioNew != null && !IDUsuarioNew.equals(IDUsuarioOld)) {
                IDUsuarioNew.getProductosCollection().add(productos);
                IDUsuarioNew = em.merge(IDUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getIDProducto();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getIDProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            Categorias IDCategoria = productos.getIDCategoria();
            if (IDCategoria != null) {
                IDCategoria.getProductosCollection().remove(productos);
                IDCategoria = em.merge(IDCategoria);
            }
            Usuario IDUsuario = productos.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario.getProductosCollection().remove(productos);
                IDUsuario = em.merge(IDUsuario);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
