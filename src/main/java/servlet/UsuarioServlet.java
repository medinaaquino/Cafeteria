package servlet;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Usuario;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  processRequest(request, response);
      
     //  response.sendRedirect("IngresarUsuario.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        EntityManager em = emf.createEntityManager();

        try {
            if ("create".equals(action)) {
                // Crear nuevo usuario
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String email = request.getParameter("email");
                String contra = request.getParameter("contra");
                String telefono = request.getParameter("telefono");
                String rol = request.getParameter("rol");

                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setApellido(apellido);
                nuevoUsuario.setEmail(email);
                nuevoUsuario.setContra(contra);
                nuevoUsuario.setTelefono(telefono);
                nuevoUsuario.setRol(rol);

                em.getTransaction().begin();
                em.persist(nuevoUsuario);
                em.getTransaction().commit();
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);

            } else if ("update".equals(action)) {
                // Actualizar usuario existente
                Integer id = Integer.parseInt(request.getParameter("id"));
                Usuario usuario = em.find(Usuario.class, id);

                if (usuario != null) {
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    String email = request.getParameter("email");
                    String contra = request.getParameter("contra");
                    String telefono = request.getParameter("telefono");
                    String rol = request.getParameter("rol");

                    em.getTransaction().begin();
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);
                    usuario.setEmail(email);
                    usuario.setContra(contra);
                    usuario.setTelefono(telefono);
                    usuario.setRol(rol);
                    em.getTransaction().commit();
                }
                response.sendRedirect("UsuarioServlet?action=list");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
