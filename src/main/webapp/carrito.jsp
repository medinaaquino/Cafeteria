<%@ page import="modelos.Productos"%>
<%@ page import="java.util.List" %>
<%@ page import="modelos.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Compras - Cafetería Mazapán</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Cafetería Mazapán</h1>
        <h2>Carrito de Compras</h2>
    </header>

    <section class="carrito">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Precio Unitario</th>
                    <th>Tamaño</th>
                    <th>Promoción</th>
                    <th>Imagen</th>
                    <th>Cantidad</th>
                    <th>Sub Total</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Productos> productos = (List<Productos>) request.getAttribute("productos");
                    if (productos == null || productos.isEmpty()) {
                %>
                <tr>
                    <td colspan="8">El carrito está vacío</td>
                </tr>
                <%
                    } else {
                        int contador = 0;
                        for (Productos producto : productos) {
                            contador++;
                %>
                <tr>
                    <td><%= contador %></td>
                    <td><%= producto.getNombre() %></td>
                    <td><%= "$" + String.format("%.2f", producto.getPrecio()) %></td>
                    <td><%= producto.getTamano() %></td>
                    <td><%= producto.getPromocion() %></td>
                    <td><img src="<%= producto.getImagenURL() %>" alt="<%= producto.getNombre() %>" width="50" height="50"></td>
                    <td>
                        <button onclick="decrement(<%= contador %>)">-</button>
                        <input type="number" id="numberField_<%= contador %>" value="1" min="1" style="width: 50px; text-align: center;" readonly>
                        <button onclick="increment(<%= contador %>)">+</button>
                    </td>
                    <td id="total_<%= contador %>">
                        <%= "$" + String.format("%.2f", producto.getPrecio()) %>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>

        <div class="total">
            <p>Total a Pagar: <strong id="totalGeneral">$0.00</strong></p>
        </div>

        <div class="pago">
            <button>Pagar con PayPal</button>
        </div>

        <div class="pago">
            <a href="cliente.jsp" class="btn">Volver</a>
        </div>
    </section>

    <footer>
        <p>&copy; 2024 Cafetería Mazapán</p>
    </footer>

    <script>
        // Funciones para manejar el carrito y la actualización de totales
        function getPrice(productId) {
            return parseFloat(document.getElementById('price_' + productId).innerText.replace('$', ''));
        }

        function increment(productId) {
            var numberField = document.getElementById('numberField_' + productId);
            var currentValue = parseInt(numberField.value);
            numberField.value = currentValue + 1;
            saveQuantity(productId, numberField.value);
            updateTotal(productId);
        }

        function decrement(productId) {
            var numberField = document.getElementById('numberField_' + productId);
            var currentValue = parseInt(numberField.value);
            if (currentValue > 1) {
                numberField.value = currentValue - 1;
                saveQuantity(productId, numberField.value);
                updateTotal(productId);
            }
        }

        function updateTotal(productId) {
            var numberField = parseInt(document.getElementById('numberField_' + productId).value);
            var price = getPrice(productId);
            var total = price * numberField;
            document.getElementById('total_' + productId).innerText = total.toFixed(2);
            updateTotalGeneral();
        }

        function updateTotalGeneral() {
            var totalGeneral = 0;
            var productCount = <%= contador %>;

            for (var i = 1; i <= productCount; i++) {
                var totalProduct = parseFloat(document.getElementById('total_' + i).innerText);
                totalGeneral += totalProduct;
            }

            document.getElementById('totalGeneral').innerText = '$' + totalGeneral.toFixed(2);
        }

        function saveQuantity(productId, quantity) {
            localStorage.setItem('quantity_' + productId, quantity);
        }

        function initializeTotals() {
            var productCount = <%= contador %>;
            for (var i = 1; i <= productCount; i++) {
                var storedQuantity = localStorage.getItem('quantity_' + i);
                if (storedQuantity) {
                    document.getElementById('numberField_' + i).value = storedQuantity;
                    updateTotal(i);
                }
            }
        }

        window.onload = initializeTotals;
    </script>
</body>
</html>
