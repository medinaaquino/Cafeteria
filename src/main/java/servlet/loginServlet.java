/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;


import controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Usuario;
/**
 *
 * @author Edgar
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los datos del formulario
        String email = request.getParameter("email");  // Correo electrónico en lugar de 'username'
        String password = request.getParameter("password");

        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);

        // Usar el método de UsuarioJpaController para validar al usuario y obtener su rol
        String role = usuarioJpaController.validateUserAndGetRole(email, password);  // Método modificado

        // Si el usuario existe y la contraseña es correcta
        if (role != null) {
            // Si el rol es 'admin', redirigir al inicio del administrador
            if ("Administrador".equals(role)) {
                response.sendRedirect("menuAdmin.jsp");
               // response.sendRedirect("menuAdmin.jsp");
            }
            // Si el rol es 'user', redirigir al inicio del usuario
            else if ("Usuario".equals(role)) {
                response.sendRedirect("menu.jsp");
            }
        } else {
            // Si el login falla, enviar un mensaje de error
            request.setAttribute("errorMessage", "Correo electrónico o contraseña incorrectos");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet de login que valida el usuario por correo electrónico y redirige según el rol";
    }
}
