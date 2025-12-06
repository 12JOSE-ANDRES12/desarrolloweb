# Sistema de Administración - Taquería

## Descripción

Se ha implementado un sistema completo de administración que permite a los usuarios con rol **ADMIN** acceder a un panel de control exclusivo donde pueden:

- Ver estadísticas generales del sistema (total usuarios, pedidos, ingresos)
- Gestionar usuarios registrados
- Ver y gestionar pedidos del sistema

## Características Principales

### 1. Rol de Usuario (UserRole)

Los usuarios ahora tienen un rol asignado:
- **ADMIN**: Acceso a panel administrativo
- **USER**: Acceso normal a la tienda

### 2. Usuario Administrador por Defecto

Al iniciar la aplicación, se crea automáticamente un usuario administrador si no existe:

```
Email: admin@taqueria.com
Contraseña: admin123
```

**IMPORTANTE**: Cambia esta contraseña en producción.

### 3. Redirección Automática

- Los **ADMIN** al iniciar sesión son redirigidos a `/admin/dashboard`
- Los **USER** al iniciar sesión son redirigidos a `/menu`

### 4. Panel de Administración

#### Rutas disponibles:

| Ruta | Descripción |
|------|-------------|
| `/admin/dashboard` | Panel principal con estadísticas y resumen |
| `/admin/usuarios` | Lista completa de usuarios |
| `/admin/pedidos` | Lista completa de pedidos |

#### Estadísticas en Dashboard:

- **Total Usuarios**: Cantidad de usuarios registrados
- **Total Pedidos**: Cantidad de pedidos realizados
- **Pedidos Pendientes**: Cantidad de pedidos en estado PENDIENTE
- **Ingresos Totales**: Suma de todos los totales de pedidos

### 5. Tablas de Datos

#### Usuarios

| Campo | Descripción |
|-------|-------------|
| ID | Identificador único |
| Nombre | Nombre del usuario |
| Email | Correo electrónico |
| Rol | ADMIN o USER |
| Fecha Registro | Cuándo se registró |
| Acciones | Ver/Editar |

#### Pedidos

| Campo | Descripción |
|-------|-------------|
| ID | Identificador del pedido |
| Cliente | Nombre del usuario |
| Subtotal | Monto antes de impuestos y envío |
| Impuestos | 10% del subtotal |
| Envío | Costo de envío |
| Total | Suma de todos los anteriores |
| Estado | PENDIENTE, PROCESANDO, COMPLETADO |
| Descripción | Detalle del pedido |
| Fecha | Cuándo se realizó |
| Acciones | Ver/Editar |

## Modelos de Datos

### Modelo: Pedido

```java
@Entity
@Table(name = "tbl_pedidos")
public class Pedido {
    Long id;
    usermodel usuario;
    Double subtotal;
    Double impuestos;
    Double envio;
    Double total;
    String estado;  // PENDIENTE, PROCESANDO, COMPLETADO, CANCELADO
    String descripcion;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
```

### Enum: UserRole

```java
public enum UserRole {
    ADMIN, USER
}
```

## Actualización del Modelo User

Se añadió el campo `role` al modelo `usermodel`:

```java
@Column(name = "role", nullable = false)
@Enumerated(EnumType.STRING)
UserRole role;
```

El valor por defecto es **USER** al registrarse un nuevo usuario.

## Endpoints de la API (Swagger)

En `/swagger-ui.html` puedes ver:

- **Users** (Usuarios)
  - `POST /users/registro` - Registrar usuario
  - `POST /users/login` - Iniciar sesión
  - `GET /users/logout` - Cerrar sesión
  - `GET /users/listar` - Listar usuarios

- **Admin** (Administración)
  - `GET /admin/dashboard` - Panel principal
  - `GET /admin/usuarios` - Lista de usuarios
  - `GET /admin/pedidos` - Lista de pedidos

## Instrucciones de Uso

### 1. Iniciar la Aplicación

```bash
cd v1
./mvnw.cmd spring-boot:run
```

O compilar y ejecutar:

```bash
./mvnw.cmd package -DskipTests
java -jar target/v1-0.0.1-SNAPSHOT.jar
```

### 2. Acceder como Administrador

1. Ve a `http://localhost:8085/`
2. Inicia sesión con:
   - **Email**: `admin@taqueria.com`
   - **Contraseña**: `admin123`
3. Serás redirigido automáticamente a `/admin/dashboard`

### 3. Registrar Usuarios Normales

1. En la página principal, usa el formulario de registro
2. Crea un usuario con email y contraseña
3. Al iniciar sesión, irá a `/menu` (vista normal)

### 4. Ver Estadísticas

En el dashboard puedes ver:
- Gráficas de estadísticas
- Últimos 5 usuarios registrados
- Últimos 5 pedidos realizados
- Enlaces rápidos a lista completa

## Estructura de Archivos Nuevos

```
src/main/java/com/desarrollo/v1/
├── model/
│   ├── UserRole.java          (Nuevo - Enum de roles)
│   ├── Pedido.java            (Nuevo - Modelo de pedidos)
│   └── usermodel.java         (Modificado - Añadido campo role)
│
├── repository/
│   └── PedidoRepository.java  (Nuevo)
│
├── service/
│   ├── UserService.java       (Modificado - Método crear admin)
│   └── PedidoService.java     (Nuevo)
│
└── controller/
    ├── AdminController.java   (Nuevo)
    └── usercontroller.java    (Modificado - Login redirige por rol)

src/main/resources/templates/admin/
├── dashboard.html             (Nuevo - Panel principal)
├── usuarios.html              (Nuevo - Lista usuarios)
└── pedidos.html               (Nuevo - Lista pedidos)
```

## Persistencia de Datos

- Los usuarios son almacenados en `tbl_users`
- Los pedidos son almacenados en `tbl_pedidos`
- La BD se actualiza automáticamente con la creación de admin
- Usa MySQL (como se configura en `application.properties`)

## Seguridad (Notas Importantes)

- **Contraseñas**: Se almacenan en texto plano. Se recomienda implementar **BCrypt** en producción.
- **Autenticación**: Se usa sesión HTTP (session.setAttribute). Considera JWT para APIs REST.
- **Autorización**: Verifica rol en cada endpoint admin (`esAdmin()` en AdminController).

## Próximos Pasos (Sugerencias)

1. **Encriptar contraseñas**: Usar Spring Security con BCrypt
2. **Crear pedidos**: Integrar CarritoController para crear `Pedido` cuando se confirma compra
3. **Editar estados**: Añadir funcionalidad para cambiar estado de pedidos (PENDIENTE → PROCESANDO → COMPLETADO)
4. **Reportes**: Generar reportes PDF/Excel de ventas
5. **Autenticación OAuth**: Integrar Google/Facebook login

## Cambios en application.properties

Se mantuvo la configuración existente. Si necesitas cambiar el puerto o la BD, edita:

```properties
server.port=8085
spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...
```

## Contacto y Soporte

Para preguntas sobre el sistema admin, revisa:
- `/admin/dashboard` - Panel principal
- Documentación API en `/swagger-ui.html`
- Este archivo README
