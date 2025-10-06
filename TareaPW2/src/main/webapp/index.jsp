<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menú Principal - Tarea Práctica #1</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <header>
            <p class="student-info">Nombre: Oscar Noe Espino Aguirre</p>
            <p class="student-info">Cuenta: 202310110465</p>
            <p class="student-info">Nombre: Luis Eduardo Sagastume Rodríguez</p>
            <p class="student-info">Cuenta: 201910040199</p>
            <h1>Menú Principal - Operaciones Disponibles</h1>
        </header>

        <!-- Sección de Conversiones Binarias -->
        <div class="menu-section">
            <h3>Conversiones Binarias</h3>
            <form action="binarios" method="post">
                <input type="text" name="numero" required 
                       placeholder="Ingrese el número">
                <div class="button-group">
                    <input type="submit" name="operacion" value="Binario a Decimal">
                    <input type="submit" name="operacion" value="Decimal a Binario">
                </div>
            </form>
        </div>

        <!-- Sección de Operaciones con Números -->
        <div class="menu-section">
            <h3>Operaciones con Números</h3>
            <form action="numeros" method="post" id="tresNumeros">
                <div class="input-group">
                    <input type="number" name="num1" required placeholder="Primer número">
                    <input type="number" name="num2" required placeholder="Segundo número">
                    <input type="number" name="num3" required placeholder="Tercer número">
                </div>
                <div class="button-group">
                    <input type="submit" name="operacion" value="Encontrar Mayor">
                    <input type="submit" name="operacion" value="Encontrar Menor">
                </div>
            </form>

            <form action="numeros" method="post" id="moda" style="margin-top: 2rem;">
                <input type="text" name="numeros" required 
                       placeholder="Lista de números separados por comas (ej: 1,2,3,2,1)">
                <div class="button-group">
                    <input type="submit" name="operacion" value="Calcular Moda">
                </div>
            </form>
        </div>

        <!-- Sección de Cálculo de Hipotenusa -->
        <div class="menu-section">
            <h3>Cálculo de Hipotenusa</h3>
            <form action="hipotenusa" method="post">
                <div class="input-group">
                    <input type="number" name="catetoA" required step="any" 
                           placeholder="Cateto A">
                    <input type="number" name="catetoB" required step="any" 
                           placeholder="Cateto B">
                </div>
                <div class="button-group">
                    <input type="submit" value="Calcular Hipotenusa">
                </div>
            </form>
        </div>
    </div>
</body>
</html>