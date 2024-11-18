<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalle del Pedido</title>
        <style>
            /* Reset básico */
            body {
                margin: 0;
                font-family: 'Arial', sans-serif;
                background: #2c1b16 no-repeat center center fixed;
                background-size: cover;
                color: #fff;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                overflow: hidden;
                background-blur: 10px;
            }

            /* Contenedor del contenido */
            .container {
                text-align: center;
                background: bisque; /* Fondo oscuro y transparente */
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 15px 45px rgba(0, 0, 0, 0.5);
                max-width: 800px;
                width: 100%;
            }

            h2, h3 {
                color: #f8e9d2; /* Color suave, similar al color de un café con leche */
                font-size: 2.5rem;
                text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.4);
            }

            p {
                margin: 10px 0;
                font-size: 1.3rem;
                color: #f1e4c9;
            }

            .order-info, .shipping-info, .product-list {
                background: #2c1b16; /* Fondo blanco semitransparente */
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 10px;
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
                color: white;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table th, table td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: left;
            }

            .boton {
                display: inline-block;
                margin: 10px;
                padding: 10px 20px;
                background-color: bisque; /* Color marrón de café */
                color: black;
                text-decoration: none;
                border-radius: 5px;
                transition: transform 0.3s, box-shadow 0.3s;
            }

            .boton:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
            }

            .bnt-imprimir {
                background-color: bisque; /* Color más oscuro de marrón */
            }

            .bnt-imprimir:hover {
                background-color: bisque;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h2>Detalle del Pedido #<c:out value="${id}"/></h2>

            <div class="order-info">
                <h3>Información del Pedido</h3>
                <p><strong>Número de Pedido: </strong><c:out value="${id}"/></p>
                <p><strong>Fecha del Pedido: </strong><c:out value="${formato}"/></p>
                <p><strong>Método de Pago: </strong><c:out value="${metodo_pago}"/></p>
                <p><strong>Total: </strong><c:out value="${total}"/></p>
            </div>

            <div class="shipping-info">
                <h3>Información de Envío</h3>
                <p><strong>Dirección de Envío: </strong><c:out value="${direccion}"/></p>
                <p><strong>Número de Seguimiento: </strong><c:out value="${id}"/></p>
            </div>

            <div class="product-list">
                <h3>Ventas de la semana</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Unitario</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detalle" items="${detalles}">
                            <tr>
                                <td><c:out value="${detalle.producto}"/></td>
                                <td><c:out value="${detalle.cantidad}"/></td>
                                <td><c:out value="${detalle.precioUnitario}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="Ajustes" class="bnt boton">Volver a Perfil</a>
                <a href="javascript:window.print()" class="bnt bnt-imprimir boton">Imprimir Reporte</a>
            </div>
        </div>
    </body>
</html>
