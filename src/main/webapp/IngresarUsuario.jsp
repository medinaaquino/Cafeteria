<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios</title>
    <style>
        /* General styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #6a4f39; /* Color de fondo café */
            margin: 0;
            padding: 0;
            color: #000; /* Texto negro */
        }

        h1, h2 {
            text-align: center;
            color: #000; /* Color blanco para los títulos */
        }

        /* Form container */
        .form-container {
            background-color: #fff; /* Fondo blanco para el formulario */
            margin: 20px auto;
            padding: 30px;
            border-radius: 8px;
            max-width: 600px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f4e1d2;
            color: #6a4f39;
        }

        td {
            background-color: #f9f9f9;
        }

        /* Form inputs */
        input[type="text"], input[type="email"], input[type="password"], select {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #f9f9f9;
            font-size: 16px;
            color: #000; /* Texto negro en los campos de formulario */
        }

        input[type="text"]:focus, input[type="email"]:focus, input[type="password"]:focus, select:focus {
            outline: none;
            border-color: #6a4f39;
        }

        /* Button styles */
        .button {
            background-color: #6a4f39; /* Color de fondo café */
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
            transition: background-color 0.3s;
        }

        .button:hover {
            background-color: #5c3e2f;
        }

        /* Button for canceling or deleting */
        .button-danger {
            background-color: #dc3545;
        }

        .button-danger:hover {
            background-color: #c82333;
        }

        /* Responsive design */
        @media screen and (max-width: 768px) {
            .form-container {
                padding: 20px;
                margin: 10px;
            }

            h1, h2 {
                font-size: 24px;
            }
        }

        /* Link styles */
        .login-link {
            text-align: center;
            margin-top: 15px;
        }

        .login-link a {
            color: #6a4f39;
            text-decoration: none;
            font-weight: bold;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        /* Flex container for button and link */
        .form-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Agregar Usuario</h2>
        <form action="UsuarioServlet" method="POST">
            <input type="hidden" name="action" value="create">

            <!-- Nombre -->
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>

            <!-- Apellido -->
            <label for="apellido">Apellido:</label>
            <input type="text" id="apellido" name="apellido" required>

            <!-- Email -->
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" name="email" required>

            <!-- Contraseña -->
            <label for="contra">Contraseña:</label>
            <input type="password" id="contra" name="contra" required>

            <!-- Teléfono -->
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" required>

            <!-- Rol -->
            <label for="rol">Rol:</label>
            <select id="rol" name="rol" required>
                <option value="Administrador">Administrador</option>
                <option value="Usuario">Usuario</option>
            </select>

            <!-- Botones de acción -->
            <div class="form-actions">
                <button type="submit" class="button">Agregar Usuario</button>
                <div class="login-link">
                    <p>¿Ya tienes una cuenta? <a href="login.jsp">Regresar al login</a></p>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
