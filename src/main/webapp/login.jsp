<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            width: 100%;
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            font-size: 16px;
            display: block;
            margin-bottom: 8px;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="text"]:focus, input[type="password"]:focus {
            outline: none;
            border-color: #6a4f39;
        }

        .button {
            width: 100%;
            padding: 12px;
            background-color: #6a4f39;
            color: white;
            border: none;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }

        .button:hover {
            background-color: #5c3e2f;
        }

        .error-message {
            color: red;
            text-align: center;
        }

        .register-link {
            text-align: center;
            margin-top: 15px;
        }

        .register-link a {
            color: #6a4f39;
            text-decoration: none;
            font-weight: bold;
        }

        .register-link a:hover {
            text-decoration: underline;
        }
        
    </style>
</head>
<body>
    <div class="container">
        <h2>Iniciar sesión</h2>
        <form action="loginServlet" method="POST">
            <label for="email">Correo Electrónico:</label>
            <input type="text" id="email" name="email" required placeholder="Ingrese su correo electrónico">

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required placeholder="Ingrese su contraseña">

            <button type="submit" class="button">Iniciar sesión</button>

            <%-- Error message display (if any) --%>
            <div class="error-message">
                <% 
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                        out.println(errorMessage);
                    }
                %>
            </div>
        </form>

        <div class="register-link">
            <p>¿No tienes una cuenta? <a href="IngresarUsuario.jsp">Regístrate aquí</a></p>
        </div>
    </div>
</body>
</html>
