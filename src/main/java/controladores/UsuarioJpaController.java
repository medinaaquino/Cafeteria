/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Ventas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelos.Productos;
import modelos.Usuario;

/**
 *
 * @author Edgar
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UsuarioJpaController() {
        this.emf=Persistence.createEntityManagerFactory("PU");
    }
    
    

    public void create(Usuario usuario) {
        if (usuario.getVentasCollection() == null) {
            usuario.setVentasCollection(new ArrayList<Ventas>());
        }
        if (usuario.getProductosCollection() == null) {
            usuario.setProductosCollection(new ArrayList<Productos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
            for (Ventas ventasCollectionVentasToAttach : usuario.getVentasCollection()) {
                ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdVenta());
                attachedVentasCollection.add(ventasCollectionVentasToAttach);
            }
            usuario.setVentasCollection(attachedVentasCollection);
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : usuario.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getIDProducto());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            usuario.setProductosCollection(attachedProductosCollection);
            em.persist(usuario);
            for (Ventas ventasCollectionVentas : usuario.getVentasCollection()) {
                Usuario oldIdUsuarioOfVentasCollectionVentas = ventasCollectionVentas.getIdUsuario();
                ventasCollectionVentas.setIdUsuario(usuario);
                ventasCollectionVentas = em.merge(ventasCollectionVentas);
                if (oldIdUsuarioOfVentasCollectionVentas != null) {
                    oldIdUsuarioOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
                    oldIdUsuarioOfVentasCollectionVentas = em.merge(oldIdUsuarioOfVentasCollectionVentas);
                }
            }
            for (Productos productosCollectionProductos : usuario.getProductosCollection()) {
                Usuario oldIDUsuarioOfProductosCollectionProductos = productosCollectionProductos.getIDUsuario();
                productosCollectionProductos.setIDUsuario(usuario);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldIDUsuarioOfProductosCollectionProductos != null) {
                    oldIDUsuarioOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldIDUsuarioOfProductosCollectionProductos = em.merge(oldIDUsuarioOfProductosCollectionProductos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Collection<Ventas> ventasCollectionOld = persistentUsuario.getVentasCollection();
            Collection<Ventas> ventasCollectionNew = usuario.getVentasCollection();
            Collection<Productos> productosCollectionOld = persistentUsuario.getProductosCollection();
            Collection<Productos> productosCollectionNew = usuario.getProductosCollection();
            List<String> illegalOrphanMessages = null;
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productos " + productosCollectionOldProductos + " since its IDUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ventas> attachedVentasCollectionNew = new ArrayList<Ventas>();
            for (Ventas ventasCollectionNewVentasToAttach : ventasCollectionNew) {
                ventasCollectionNewVentasToAttach = em.getReference(ventasCollectionNewVentasToAttach.getClass(), ventasCollectionNewVentasToAttach.getIdVenta());
                attachedVentasCollectionNew.add(ventasCollectionNewVentasToAttach);
            }
            ventasCollectionNew = attachedVentasCollectionNew;
            usuario.setVentasCollection(ventasCollectionNew);
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIDProducto());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            usuario.setProductosCollection(productosCollectionNew);
            usuario = em.merge(usuario);
            for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
                if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
                    ventasCollectionOldVentas.setIdUsuario(null);
                    ventasCollectionOldVentas = em.merge(ventasCollectionOldVentas);
                }
            }
            for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
                if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
                    Usuario oldIdUsuarioOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdUsuario();
                    ventasCollectionNewVentas.setIdUsuario(usuario);
                    ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
                    if (oldIdUsuarioOfVentasCollectionNewVentas != null && !oldIdUsuarioOfVentasCollectionNewVentas.equals(usuario)) {
                        oldIdUsuarioOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
                        oldIdUsuarioOfVentasCollectionNewVentas = em.merge(oldIdUsuarioOfVentasCollectionNewVentas);
                    }
                }
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Usuario oldIDUsuarioOfProductosCollectionNewProductos = productosCollectionNewProductos.getIDUsuario();
                    productosCollectionNewProductos.setIDUsuario(usuario);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldIDUsuarioOfProductosCollectionNewProductos != null && !oldIDUsuarioOfProductosCollectionNewProductos.equals(usuario)) {
                        oldIDUsuarioOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldIDUsuarioOfProductosCollectionNewProductos = em.merge(oldIDUsuarioOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Productos> productosCollectionOrphanCheck = usuario.getProductosCollection();
            for (Productos productosCollectionOrphanCheckProductos : productosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Productos " + productosCollectionOrphanCheckProductos + " in its productosCollection field has a non-nullable IDUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ventas> ventasCollection = usuario.getVentasCollection();
            for (Ventas ventasCollectionVentas : ventasCollection) {
                ventasCollectionVentas.setIdUsuario(null);
                ventasCollectionVentas = em.merge(ventasCollectionVentas);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
public String validateUserAndGetRole(String email, String password) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        // Buscar el usuario por su correo electrónico
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        query.setParameter("email", email);  // Cambié 'username' por 'email'
        List<Usuario> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            Usuario usuario = resultList.get(0);
            // Aquí puedes agregar el código para comparar las contraseñas de forma segura
            // Suponiendo que la contraseña esté almacenada en texto claro (aunque NO se recomienda)
            if (usuario.getContra().equals(password)) {
                // Si la contraseña es válida, obtener el rol
                return usuario.getRol();  // Devuelve el rol del usuario
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (em != null) {
            em.close();
        }
    }
    return null; // Si no se encuentra el usuario o la contraseña es incorrecta
}


}
