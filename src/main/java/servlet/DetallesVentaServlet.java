package servlet;

import modelos.DetallesVenta;
import modelos.Ventas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DetallesVentaServlet", urlPatterns = {"/detallesVenta"})
public class DetallesVentaServlet extends HttpServlet {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cafeteriaPU");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperar el parámetro 'id_venta' de la solicitud
        String idVentaStr = request.getParameter("id_venta");
        if (idVentaStr == null || idVentaStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID de venta es obligatorio.");
            return;
        }

        try {
            int idVenta = Integer.parseInt(idVentaStr);

            // Consultar la venta y sus detalles
            EntityManager em = emf.createEntityManager();
            Ventas venta = em.find(Ventas.class, idVenta);

            if (venta == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró la venta con el ID proporcionado.");
                return;
            }

            TypedQuery<DetallesVenta> query = em.createQuery(
                    "SELECT d FROM DetallesVenta d WHERE d.venta.idVenta = :idVenta", DetallesVenta.class);
            query.setParameter("idVenta", idVenta);
            List<DetallesVenta> detalles = query.getResultList();

            em.close();

            // Enviar datos a la vista JSP
            request.setAttribute("venta", venta);
            request.setAttribute("detalles", detalles);
            request.getRequestDispatcher("/detalleVenta.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID de venta debe ser un número válido.");
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud", e);
        }
    }
}
