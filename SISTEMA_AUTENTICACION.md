# Sistema de Autenticación y Páginas de Misión y Visión - Taquería

## ✅ Cambios Implementados

### 1. **Sistema de Login y Registro**
Se ha creado un sistema completo de autenticación con:
- Formulario de inicio de sesión
- Formulario de registro de nuevos usuarios
- Validación de contraseñas
- Verificación de emails duplicados
- Manejo de sesiones HTTP

**Archivos modificados:**
- `usercontroller.java` - Nuevos endpoints POST para login y registro
- `UserService.java` - Métodos de autenticación y registro
- `userRepository.java` - Métodos adicionales para búsqueda por email
- `usermodel.java` - Nuevos campos en la entidad

### 2. **Base de Datos**
El modelo de usuario incluye:
- `id` - Identificador único (PRIMARY KEY)
- `nombre` - Nombre del usuario (50 caracteres)
- `email` - Correo electrónico (único, 100 caracteres)
- `password` - Contraseña del usuario
- `created_at` - Fecha de creación del registro

**Tabla:** `tbl_users`

### 3. **Página Principal (index.html)**
Se agregó:
- Modal de autenticación con estilos modernos
- Botones para mostrar/ocultar formularios de login y registro
- Información del usuario logueado
- Botones de cerrar sesión
- Tarjetas clickeables de Misión y Visión que redirigen a páginas detalladas

### 4. **Página de Misión** (`/mision`)
Página dedicada que incluye:
- Sección hero con gradiente rojo
- Título e introducción
- 4 tarjetas de puntos clave:
  - Ingredientes Frescos
  - Pasión por la Cocina
  - Satisfacción del Cliente
  - Preservar Tradición
- Sección de compromiso
- Enlaces a página inicial y visión

### 5. **Página de Visión** (`/vision`)
Página dedicada que incluye:
- Sección hero con gradiente verde
- Título e introducción
- 6 tarjetas de objetivos:
  - Excelencia en Calidad
  - Comunidad y Conexión
  - Innovación Continua
  - Sostenibilidad
  - Expansión Regional
  - Capacitación Permanente
- Línea de tiempo del plan futuro
- Enlaces a página inicial y misión

### 6. **Navegación (nav.html)**
Se actualizó con:
- Enlaces a las nuevas páginas (/mision, /vision)
- Botones contextuales de login/registro
- Información del usuario logueado con opción de salir

### 7. **Controlador de Índice (indexcontroller.java)**
Se agregaron mapeos para:
- GET `/` - Página de inicio
- GET `/index` - Página de inicio (alternativa)
- GET `/vision` - Página de visión
- GET `/mision` - Página de misión

## 🔐 Endpoints Disponibles

### Autenticación
- **POST** `/users/login` - Iniciar sesión
- **POST** `/users/registro` - Registrar nuevo usuario
- **GET** `/users/logout` - Cerrar sesión

### Navegación
- **GET** `/` - Página de inicio con login/registro
- **GET** `/mision` - Página de misión detallada
- **GET** `/vision` - Página de visión detallada
- **GET** `/users/listar` - Lista de usuarios (existente)

## 📝 Uso del Sistema

### Para Registrarse
1. Haz clic en el botón "Registrarse" en la página principal
2. Completa los campos: Nombre, Email, Contraseña y Confirmación
3. Haz clic en "Registrarse"
4. Si es exitoso, se redirige automáticamente

### Para Iniciar Sesión
1. Haz clic en el botón "Iniciar Sesión" en la página principal
2. Ingresa tu email y contraseña
3. Haz clic en "Iniciar Sesión"
4. Aparecerá tu información en la navegación

### Para Cerrar Sesión
1. Haz clic en el botón "Salir" en la navegación (solo visible si estás logueado)
2. Se cerrará tu sesión automáticamente

### Para Explorar Misión y Visión
1. Desde la página principal, haz clic en las tarjetas de Misión o Visión
2. O usa los enlaces en la navegación
3. Cada página tiene información detallada con múltiples puntos clave
4. Puedes navegar entre Misión y Visión desde cualquiera de las dos páginas

## 🗄️ Configuración de Base de Datos

La aplicación usa MySQL. Asegúrate de que los datos en `application.properties` sean correctos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/newbase
spring.datasource.username=root
spring.datasource.password=Tdea
spring.jpa.hibernate.ddl-auto=update
```

La tabla `tbl_users` se creará automáticamente con Hibernate.

## 🎨 Características de Diseño

- **Responsive:** Funciona en dispositivos móviles y escritorio
- **Modal Elegante:** Formularios en un modal overlay
- **Validación:** Campos requeridos y validación en cliente
- **Mensajes:** Alertas de éxito y error en la parte superior
- **Gradientes:** Diseño moderno con gradientes de color
- **Animaciones:** Transiciones suaves entre estados

## ⚠️ Nota de Seguridad

**Importante:** Las contraseñas se guardan en texto plano. Para producción, debes:
1. Implementar BCrypt para encriptar contraseñas
2. Agregar validación de contraseña más robusta
3. Implementar CSRF protection
4. Usar HTTPS

Ejemplo de implementación con BCrypt:

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(password);
```

## 📦 Dependencias Requeridas

Ya incluidas en pom.xml:
- Spring Boot Data JPA
- Spring Boot Web
- Thymeleaf
- MySQL Connector
- Lombok
- Jakarta Persistence

---

**Última actualización:** 5 de Diciembre, 2025
