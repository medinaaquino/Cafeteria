<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="modelos.Usuario" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Venta</title>
</head>
<body>
    <h1>Formulario de Venta</h1>

    <form action="VentaServlet" method="POST">
        <label for="idUsuario">Usuario:</label>
        <select name="idUsuario" id="idUsuario">
            <c:forEach var="usuario" items="${usuarios}">
                <option value="${usuario.id}">${usuario.nombre}</option>
            </c:forEach>
        </select>
        <br>

        <label for="fecha">Fecha:</label>
        <input type="date" id="fecha" name="fecha" required>
        <br>

        <label for="total">Total:</label>
        <input type="number" id="total" name="total" step="0.01" required>
        <br>

        <label for="metodoPago">Método de pago:</label>
        <input type="text" id="metodoPago" name="metodoPago" required>
        <br>

        <label for="dirreccion">Dirección:</label>
        <textarea id="dirreccion" name="dirreccion" required></textarea>
        <br>

        <button type="submit">Crear Venta</button>
    </form>

</body>
</html>
