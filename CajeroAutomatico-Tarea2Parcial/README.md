# Cajero Automático JSF - Sistema Bancario Web

Sistema completo de cajero automático implementado con **JavaServer Faces (JSF)** y **Jakarta EE**. Proporciona una interfaz web moderna y profesional para realizar operaciones bancarias básicas con gestión completa de usuarios.

## 🌟 Características Principales

### Operaciones Bancarias
- **Inicio de sesión seguro** con validación en tiempo real
- **Consulta de saldo** con historial de las últimas 3 transacciones
- **Retiro de efectivo** con confirmación previa
- **Depósito** con validación de montos
- **Transferencias** entre cuentas existentes o nuevas
- **Historial completo** de todas las transacciones
- **Confirmaciones** antes de operaciones críticas

### Sistema de Administración
- **Panel de administración** exclusivo para el banco
- **Registro de usuarios** con cuenta bancaria autorizada
- **Gestión de usuarios** - ver, eliminar usuarios del sistema
- **Control de acceso** estricto con validaciones
- **Protección** de cuenta bancaria (no eliminable)

### Interfaz y Experiencia de Usuario
- **Diseño moderno** con gradientes y efectos visuales
- **Cards interactivas** con efectos hover profesionales
- **Validación en tiempo real** con mensajes de error claros
- **Responsive design** adaptado para móviles, tablets y desktop
- **Confirmaciones visuales** para todas las operaciones
- **Mensajes informativos** en todas las interacciones

## 📋 Requisitos Técnicos

- **Java 11** o superior
- **Apache Maven** 3.6 o superior
- **Servidor de aplicaciones** compatible con Jakarta EE 10
  - GlassFish 7+
  - Payara 6+
  - WildFly 27+

## 🚀 Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/OsEspino-Sen/OscarEspino-Repositorio.git
   cd CajeroAutomatico-Tarea2Parcial
   ```

2. **Compilar el proyecto:**
   ```bash
   mvn clean package
   ```

3. **Desplegar el archivo WAR:**
   - Copiar `target/CajeroAutomatico-Tarea2Parcial-1.0-SNAPSHOT.war` a la carpeta `deploy` de tu servidor
   - Acceder a `http://localhost:8080/CajeroAutomatico-Tarea2Parcial-1.0-SNAPSHOT/`

## 👤 Cuentas de Prueba

El sistema incluye cuentas preconfiguradas:

| Número de Cuenta | PIN  | Saldo Inicial | Tipo        |
|-----------------|------|---------------|-------------|
| 1234567810     | 9999 | $0            | Banco (Admin) |
| 12345678       | 1234 | $1,000        | Cliente      |
| 87654321       | 4321 | $2,000        | Cliente      |

### Cuenta Administradora
- **Número:** `1234567810`
- **PIN:** `9999`
- **Permisos:** Puede agregar usuarios, gestionar usuarios, eliminar usuarios
- **Protección:** No puede ser eliminada del sistema

## 🎯 Operaciones Disponibles

### Para Usuarios Comunes

#### 1. Consultar Saldo
- Muestra el saldo actual formateado como moneda
- Visualiza las últimas 3 transacciones
- Botón directo para ver historial completo
- Auto-redirección si no hay sesión activa

#### 2. Retirar Efectivo
- Validación de saldo suficiente
- Confirmación previa antes de retirar
- Actualización inmediata del saldo
- Registro automático en historial

#### 3. Depositar
- Validación de monto positivo
- Confirmación previa antes de depositar
- Actualización inmediata del saldo
- Registro automático en historial

#### 4. Transferir
- Permite transferir a cuentas registradas en el sistema
- Solo se pueden transferir a cuentas que ya existen
- Validación de cuenta destino existente
- Validación de saldo suficiente
- Confirmación previa
- **Registro bidireccional completo:**
  - La cuenta origen registra "Transferencia enviada"
  - La cuenta destino registra "Transferencia recibida"
  - Ambas cuentas pueden ver la transacción en su historial
  - El saldo se actualiza en ambas cuentas automáticamente
  - Las cuentas receptoras pueden acceder y ver el depósito reflejado en su saldo y historial

#### 5. Historial
- Tabla completa de todas las transacciones
- Información de fecha, tipo, monto y saldo resultante
- Colores diferenciados por tipo de transacción
- Export visual de operaciones

### Para Administradores

#### 1. Agregar Usuario
- Acceso exclusivo con cuenta bancaria
- Registro de cuentas con 8 o 10 dígitos
- PIN de 4 dígitos obligatorio
- Saldo inicial en $0
- Validaciones estrictas

#### 2. Gestionar Usuarios
- Visualización de todos los usuarios
- Ver saldo y número de transacciones
- Eliminar usuarios (excepto cuenta bancaria)
- Control total del sistema

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── cajero/
│   │           ├── model/
│   │           │   ├── Cuenta.java              # Modelo de cuenta bancaria
│   │           │   ├── Transaccion.java          # Modelo de transacción
│   │           │   └── TipoTransaccion.java      # Enum de tipos de transacción
│   │           ├── servicio/
│   │           │   └── ServicioCuenta.java       # Lógica de negocio
│   │           └── web/
│   │               └── CajeroBean.java           # Managed Bean principal
│   ├── resources/
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── beans.xml                         # Configuración CDI
│       │   ├── faces-config.xml                  # Configuración JSF
│       │   └── web.xml                           # Configuración web
│       ├── resources/
│       │   └── css/
│       │       └── style.css                     # Estilos y diseño
│       ├── login.xhtml                           # Página de inicio de sesión
│       ├── menu.xhtml                            # Menú principal
│       ├── saldo.xhtml                           # Consulta de saldo
│       ├── retiro.xhtml                          # Retiro de efectivo
│       ├── deposito.xhtml                        # Depósito
│       ├── transferencia.xhtml                  # Transferencias
│       ├── historial.xhtml                       # Historial completo
│       ├── registrar.xhtml                       # Registro de usuarios (Admin)
│       ├── gestionar.xhtml                      # Gestión de usuarios (Admin)
│       └── ayuda.xhtml                           # Página de ayuda
```

## 🔐 Seguridad y Validaciones

### Validaciones de Datos
- **Número de cuenta:** 8 o 10 dígitos numéricos (máximo 10 caracteres)
- **PIN:** Exactamente 4 dígitos numéricos (máximo 4 caracteres)
- **Montos:** Solo valores positivos, con límite según saldo disponible
- **Longitud máxima:** Prevención de entrada adicional con `maxlength`

### Seguridad de Sesión
- **SessionScope:** Estado de sesión mantenido durante la navegación
- **ApplicationScope:** Estado global de cuentas simuladas en memoria
- **Validación de permisos:** Verificación antes de operaciones administrativas
- **Cierre de sesión:** Invalidación completa del session context

### Protecciones Implementadas
- No permite saldos negativos
- No permite transferirse a sí mismo
- Protección de cuenta bancaria (no eliminable)
- Validación en frontend (JSF) y backend (Java)
- Confirmaciones antes de operaciones destructivas

## 🎨 Decisiones de Diseño

### Tecnologías Utilizadas
- **Jakarta Faces 4.x** - Framework JSF
- **Jakarta CDI** - Inyección de dependencias
- **Jakarta Validation** - Validaciones
- **Maven** - Gestión de dependencias

### Arquitectura
- **MVC Pattern** - Separación de capas
- **Managed Beans** - Gestión de estado
- **Service Layer** - Lógica de negocio
- **Model Layer** - Entidades de dominio

### Estilos y Diseño
- **CSS Variables** - Personalización fácil
- **Gradientes modernos** - Efectos visuales
- **Cards responsivas** - Diseño adaptativo
- **Transiciones suaves** - Mejor UX
- **Iconos visuales** - Identificación rápida

## 📦 Dependencias Principales

```xml
<dependencies>
    <!-- Jakarta Faces -->
    <dependency>
        <groupId>jakarta.faces</groupId>
        <artifactId>jakarta.faces</artifactId>
    </dependency>
    
    <!-- Jakarta CDI -->
    <dependency>
        <groupId>org.jboss.weld.servlet</groupId>
        <artifactId>weld-servlet-shaded</artifactId>
    </dependency>
    
    <!-- Jakarta Validation -->
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
    </dependency>
</dependencies>
```

## 🧪 Testing

### Cuentas de Prueba
1. **Login con cuenta existente:**
   - Cuenta: `12345678`
   - PIN: `1234`

2. **Login como administrador:**
   - Cuenta: `1234567810`
   - PIN: `9999`

3. **Operaciones de prueba:**
   - Consultar saldo
   - Retirar $100
   - Depositar $50
   - Transferir a cuenta `87654321`

## 🚀 Mejoras Implementadas

### Interfaz de Usuario
- ✅ Diseño moderno con cards profesionales
- ✅ Gradientes y efectos visuales
- ✅ Header con información de sesión
- ✅ Mensajes informativos en footer
- ✅ Confirmaciones antes de operaciones

### Funcionalidades Administrativas
- ✅ Sistema de registro de usuarios
- ✅ Panel de gestión de usuarios
- ✅ Control de acceso estricto
- ✅ Protección de cuenta bancaria

### Validaciones y Seguridad
- ✅ Validación en tiempo real
- ✅ Prevención de entrada excesiva
- ✅ Confirmaciones visuales
- ✅ Mensajes de error claros

## 📞 Contacto y Información del Proyecto

**Desarrolladores:**
- Oscar Noe Espino Aguirre - Número de Cuenta: 202310110465
- Juan Rafael Batres Padilla - Número de Cuenta: 202310110503

**Email:** sensei2004@gmail.com  
**GitHub:** [https://github.com/OsEspino-Sen/OscarEspino-Repositorio](https://github.com/OsEspino-Sen/OscarEspino-Repositorio)

---

### 🌐 Funcionalidad de Transferencias Inter-Sistema

El sistema es **completamente funcional** para transferencias entre cuentas del sistema:

#### ✨ Características de Transferencia

1. **Transferencia entre Cuentas Registradas:**
   - Solo puede transferir a cuentas que ya existen en el sistema
   - La transferencia será rechazada si la cuenta destino no existe
   - La cuenta origen ve "Transferencia enviada" con monto negativo
   - La cuenta destino ve "Transferencia recibida" con monto positivo
   - Ambas cuentas tienen el registro completo de la transacción


2. **Visibilidad Completa:**
   - **Cuenta origen:** Puede ver el débito en su saldo y historial
   - **Cuenta destino:** Puede ver el crédito en su saldo y historial
   - Ambas ven fecha, monto, tipo y saldo resultante
   - Las transacciones quedan registradas permanentemente

3. **Ejemplo de Uso:**
   ```
   Cuenta A (12345678) transfiere $500 a Cuenta B (87654321)
   
   Resultado:
   - Cuenta A: Saldo -= $500, Historial muestra "Transferencia enviada"
   - Cuenta B: Saldo += $500, Historial muestra "Transferencia recibida"
   - Ambas pueden acceder y ver la transacción completa
   ```

## 📄 Licencia y Contexto Académico

Este proyecto es parte de un trabajo académico para la asignatura de **Programación Web II**.

**Estudiantes:**
- **Oscar Noe Espino Aguirre** - Número de Cuenta: 202310110465
- **Juan Rafael Batres Padilla** - Número de Cuenta: 202310110503

**Institución:** Universidad Tecnológica de Honduras (UTH)

---

## 💡 Demostración del Sistema

### Escenario Real de Transferencia

1. **Usuario A** (12345678) inicia sesión
2. Se dirige a "Transferir"
3. Ingresa cuenta destino: `87654321` y monto: `$500`
4. Confirma la operación
5. **El sistema automáticamente:**
   - Debita $500 de la cuenta 12345678
   - Acredita $500 a la cuenta 87654321
   - Registra en ambas cuentas la transacción

6. **Usuario B** (87654321) inicia sesión posteriormente
7. Ve en su saldo: +$500
8. Revisa su historial y encuentra "Transferencia recibida de 12345678 - $500"
9. **Puede realizar operaciones normalmente** con el saldo actualizado

### Sistema de Persistencia

- Todas las transacciones se registran en tiempo real
- El saldo se actualiza instantáneamente en ambas cuentas
- No se requiere recarga o reinicio del sistema
- Las transacciones persisten durante toda la sesión de la aplicación

---

**Desarrollado con ❤️ usando JSF y Jakarta EE**
