# Cajero Automático JSF

Este proyecto es una simulación de un cajero automático implementado con JavaServer Faces (JSF). Proporciona una interfaz web para realizar operaciones bancarias básicas como consultas de saldo, retiros, depósitos y transferencias.

## Características

- Inicio de sesión seguro con número de cuenta y PIN
- Consulta de saldo
- Retiro de efectivo
- Depósito de dinero
- Transferencias entre cuentas
- Historial de transacciones
- Tema claro/oscuro
- Diseño responsive
- Validaciones en tiempo real
- Mensajes de confirmación y error

## Requisitos

- Java 23 o superior
- Apache Maven 3.6 o superior
- Servidor de aplicaciones compatible con Jakarta EE 10 (por ejemplo, GlassFish 7)

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/OsEspino-Sen/OscarEspino-Repositorio.git
   ```

2. Navegar al directorio del proyecto:
   ```bash
   cd CajeroAutomatico-Tarea2Parcial
   ```

3. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

4. Desplegar el archivo WAR generado en tu servidor de aplicaciones.

## Uso

### Cuentas de prueba
El sistema viene con tres cuentas preconfiguradas para pruebas:

| Número de Cuenta | PIN  | Saldo Inicial |
|-----------------|------|---------------|
| 1234           | 1111 | $1,000       |
| 5678           | 2222 | $2,000       |
| 9012           | 3333 | $3,000       |

### Operaciones disponibles

1. **Inicio de sesión**
   - Ingresar número de cuenta (4 dígitos)
   - Ingresar PIN (4 dígitos)

2. **Consulta de saldo**
   - Muestra el saldo actual de la cuenta
   - Registra la consulta en el historial

3. **Retiro de efectivo**
   - Validación de saldo suficiente
   - Actualización inmediata del saldo
   - Registro en historial

4. **Depósito**
   - Validación de monto positivo
   - Actualización inmediata del saldo
   - Registro en historial

5. **Transferencia**
   - Validación de cuenta destino existente
   - Validación de saldo suficiente
   - Registro en historial de ambas cuentas

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── cajero/
│   │           ├── model/
│   │           │   ├── Account.java
│   │           │   ├── Transaction.java
│   │           │   └── TransactionType.java
│   │           ├── service/
│   │           │   └── AccountService.java
│   │           └── web/
│   │               └── CajeroBean.java
│   ├── resources/
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── beans.xml
│       │   └── faces-config.xml
│       ├── resources/
│       │   └── css/
│       │       └── style.css
│       ├── ayuda.xhtml
│       ├── deposito.xhtml
│       ├── historial.xhtml
│       ├── index.xhtml
│       ├── login.xhtml
│       ├── menu.xhtml
│       ├── retiro.xhtml
│       ├── saldo.xhtml
│       └── transferencia.xhtml
```

## Decisiones de Diseño

### Scopes utilizados
- **@SessionScoped** para `CajeroBean`: Mantiene el estado de la sesión del usuario.
- **@ApplicationScoped** para `AccountService`: Mantiene el estado global de las cuentas simuladas.

### Validaciones
- Validación de PIN en el modelo `Account`
- Validaciones de montos usando `f:validateDoubleRange`
- Validaciones de longitud usando `f:validateLength`
- Mensajes de error personalizados

### Seguridad
- Validación de sesión activa
- Cierre de sesión automático
- Protección contra saldos negativos
- Validación de cuentas existentes

## Mejoras Implementadas

1. **Tema Oscuro/Claro**
   - Cambio dinámico de tema
   - Persistencia durante la sesión
   - Diseño adaptativo

2. **Historial de Transacciones**
   - Registro detallado de operaciones
   - Visualización en tabla ordenada
   - Diferenciación de tipos de transacciones

3. **Interfaz Responsiva**
   - Diseño adaptable a diferentes dispositivos
   - Menú grid con ajuste automático
   - Botones y formularios optimizados para móviles

## Contacto

Oscar Espino - [oscar.espino@example.com](mailto:oscar.espino@example.com)

Proyecto Link: [https://github.com/OsEspino-Sen/OscarEspino-Repositorio](https://github.com/OsEspino-Sen/OscarEspino-Repositorio)