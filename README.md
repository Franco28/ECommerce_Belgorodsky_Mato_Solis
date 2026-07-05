<div align="center">

# E-Commerce Final - Programación Avanzada

Proyecto Java por etapas para el examen final integrador de Programación Avanzada.

</div>

---

## Índice

- [Etapa 1 - Modelo de dominio](#etapa-1---modelo-de-dominio)
- [Etapa 2 - Excepciones personalizadas y validaciones base](#etapa-2---excepciones-personalizadas-y-validaciones-base)
- [Etapa 3 - Persistencia SQLite base](#etapa-3---persistencia-sqlite-base)
- [Etapa 4 - DAO y Factory](#etapa-4---dao-y-factory)
- [Etapa 5 - Servicios de negocio](#etapa-5---servicios-de-negocio)
- [Etapa 6 - Usuarios y roles por consola](#etapa-6---usuarios-y-roles-por-consola)
- [Etapa 7 - Productos, categorías e inventario por consola](#etapa-7---productos-categorías-e-inventario-por-consola)
- [Etapa 8 - Carrito de compras por consola](#etapa-8---carrito-de-compras-por-consola)
- [Etapa 9 - Procesamiento de pagos por consola](#etapa-9---procesamiento-de-pagos-por-consola)
- [Etapa 10 - Órdenes de compra y checkout integrado](#etapa-10---órdenes-de-compra-y-checkout-integrado)
- [Etapa 11 - Envíos y seguimiento de pedidos](#etapa-11---envíos-y-seguimiento-de-pedidos)
- [Etapa 12 - Reclamos, devoluciones y calificaciones](#etapa-12---reclamos-devoluciones-y-calificaciones)
- [Etapa 13 - Reportes de gestión](#etapa-13---reportes-de-gestión)
- [Compilar el proyecto](#compilar-el-proyecto)
- [Inicializar SQLite](#inicializar-sqlite)
- [Ejecutar con Maven](#ejecutar-con-maven)

---

## Etapa 1 - Modelo de dominio

Incluye:

- Enums principales del negocio.
- Interfaces obligatorias y complementarias.
- Clase abstracta `Producto`.
- Subclases `ProductoFisico`, `ProductoDigital` y `ProductoImportado`.
- Entidades principales del ciclo de compra:
  - Usuario
  - Categoria
  - Producto
  - InventarioMovimiento
  - Carrito
  - ItemCarrito
  - OrdenCompra
  - ItemOrden
  - Pago
  - Envio
  - Reclamo
  - Devolucion
  - Calificacion

## Etapa 2 - Excepciones personalizadas y validaciones base

Se agregó el paquete:

```text
src/main/java/ecommerce/exception
```

Excepciones obligatorias incorporadas:

- `ProductoDuplicadoException`
- `ProductoNoEncontradoException`
- `UsuarioNoEncontradoException`
- `StockInsuficienteException`
- `CarritoVacioException`
- `PagoRechazadoException`
- `OrdenNoEncontradaException`
- `EnvioNoEncontradoException`
- `CategoriaNoEncontradaException`
- `PermisoDenegadoException`
- `DatosInvalidosException`

También se agregó:

```text
src/main/java/ecommerce/util/ValidadorDominio.java
```

Ese validador centraliza reglas básicas como:

- campos obligatorios,
- IDs no negativos,
- cantidades mayores a cero,
- precios mayores a cero,
- valores no negativos,
- formato básico de email,
- objetos obligatorios.

Las entidades principales fueron ajustadas para usar excepciones propias del dominio en lugar de depender solamente de excepciones genéricas como `IllegalArgumentException`.

## Etapa 3 - Persistencia SQLite base

Se agregó infraestructura para preparar la persistencia real entre ejecuciones.

Archivos principales:

```text
src/main/java/ecommerce/database/DatabaseConfig.java
src/main/java/ecommerce/database/DatabaseConnection.java
src/main/java/ecommerce/database/DatabaseInitializer.java
src/main/java/ecommerce/database/DatabaseInitializerApp.java
src/main/java/ecommerce/database/SqlScriptRunner.java
src/main/resources/database/schema.sql
```

También se agregó:

```text
src/main/java/ecommerce/exception/DatabaseException.java
database/README.md
database/.gitkeep
```

La Etapa 3 crea el esquema inicial para:

- usuarios,
- categorías,
- productos,
- inventario,
- carrito,
- pagos,
- envíos,
- órdenes,
- reclamos,
- devoluciones,
- calificaciones.

## Decisión técnica

Las excepciones personalizadas extienden de `EcommerceException`, que a su vez extiende de `RuntimeException`.

Esto permite mantener los modelos limpios y evita llenar constructores, setters y métodos simples con firmas `throws`.

Para persistencia se utiliza SQLite. En esta etapa se define la conexión, configuración y creación de tablas.

## Etapa 4 - DAO y Factory

Paquetes principales:

```text
src/main/java/ecommerce/dao/interfaces
src/main/java/ecommerce/dao/sqlite
src/main/java/ecommerce/dao/factory
```

Interfaces DAO incluidas:

```text
UsuarioDAO
CategoriaDAO
ProductoDAO
InventarioDAO
PagoDAO
EnvioDAO
OrdenDAO
ReclamoDAO
```

Implementaciones SQLite incluidas:

```text
SQLiteUsuarioDAO
SQLiteCategoriaDAO
SQLiteProductoDAO
SQLiteInventarioDAO
SQLitePagoDAO
SQLiteEnvioDAO
SQLiteOrdenDAO
SQLiteReclamoDAO
```

Factory incluida:

```text
DAOFactory
SQLiteDAOFactory
```

Ejemplo de uso:

```java
DAOFactory factory = DAOFactory.obtenerFactory();
ProductoDAO productoDAO = factory.crearProductoDAO();
```

## Etapa 5 - Servicios de negocio

La Etapa 5 agrega la capa de servicios entre el menú por consola y los DAOs.

Paquete principal:

```text
src/main/java/ecommerce/service
```

Servicios incluidos:

```text
SeguridadService
UsuarioService
CategoriaService
ProductoService
InventarioService
CarritoService
PagoService
EnvioService
OrdenService
ReclamoService
ReporteService
ServiceFactory
```

Responsabilidades principales:

- Validar permisos por rol.
- Validar usuarios activos.
- Evitar emails, categorías y productos duplicados.
- Controlar stock disponible.
- Registrar movimientos de inventario.
- Administrar carrito usando productos persistidos.
- Registrar pagos y envíos.
- Crear órdenes desde el carrito.
- Descontar stock al confirmar una orden.
- Vaciar el carrito después de comprar.
- Generar reclamos sobre órdenes existentes.
- Calcular reportes básicos de gestión.

## Etapa 6 - Usuarios y roles por consola

Se agregó el paquete:

```text
src/main/java/ecommerce/ui
```

Clases principales:

```text
AplicacionConsola
MenuPrincipal
UsuarioMenu
RolMenu
EntradaConsola
RolSelector
ConsolaUtils
```

Funcionalidades disponibles desde consola:

- Registrar usuario.
- Modificar usuario.
- Eliminar usuario.
- Buscar usuario por ID.
- Buscar usuario por email.
- Listar usuarios.
- Activar usuario.
- Desactivar usuario.
- Listar roles del sistema.
- Consultar permisos asociados a cada rol.

## Etapa 7 - Productos, categorías e inventario por consola

Se completó la integración de los módulos de productos, categorías e inventario dentro del menú principal.

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
CategoriaMenu
ProductoMenu
InventarioMenu
CategoriaSelector
EstadoProductoSelector
```

También se ampliaron:

```text
MenuPrincipal
EntradaConsola
ConsolaUtils
```

Funcionalidades disponibles desde consola para categorías:

- Alta de categoría.
- Modificación de categoría.
- Eliminación de categoría.
- Búsqueda por ID.
- Búsqueda por nombre.
- Listado general.
- Activación.
- Desactivación.

Funcionalidades disponibles desde consola para productos:

- Registro de productos físicos, digitales e importados.
- Modificación de datos generales y datos específicos según el tipo de producto.
- Eliminación de productos.
- Búsqueda por ID.
- Búsqueda por código.
- Listado general.
- Listado por categoría.
- Listado de productos sin stock.
- Activación.
- Inactivación.
- Suspensión.

Funcionalidades disponibles desde consola para inventario:

- Ingreso de stock.
- Egreso de stock.
- Ajuste de stock.
- Consulta de stock.
- Historial de movimientos por producto.
- Listado general de movimientos.

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
```

## Etapa 8 - Carrito de compras por consola

Se integró el módulo de carrito dentro del menú principal.

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
CarritoMenu
ClienteSelector
```

También se ampliaron:

```text
MenuPrincipal
ConsolaUtils
```

Funcionalidades disponibles desde consola para carrito:

- Seleccionar cliente activo.
- Crear o recuperar el carrito activo del cliente seleccionado.
- Agregar productos al carrito mediante código de producto.
- Eliminar productos del carrito.
- Modificar cantidades.
- Vaciar carrito.
- Visualizar el contenido del carrito.
- Calcular subtotal.
- Calcular total.
- Listar productos disponibles para agregar al carrito.

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
```

El carrito utiliza productos persistidos para validar disponibilidad y stock actualizado. Las órdenes, pagos y envíos quedan para las siguientes partes del flujo de compra.

## Etapa 9 - Procesamiento de pagos por consola

Se integró el módulo de pagos dentro del menú principal.

Paquete agregado:

```text
src/main/java/ecommerce/payment
```

Clases agregadas:

```text
ProcesadorPagoFactory
ProcesadorTarjetaCredito
ProcesadorTarjetaDebito
ProcesadorTransferenciaBancaria
ProcesadorBilleteraVirtual
ProcesadorPagoContraEntrega
```

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
PagoMenu
MetodoPagoSelector
```

También se ampliaron:

```text
PagoService
MenuPrincipal
ConsolaUtils
```

Funcionalidades disponibles desde consola para pagos:

- Procesar pago mediante Strategy.
- Registrar pago pendiente.
- Buscar pago por ID.
- Listar pagos.
- Aprobar pago pendiente.
- Rechazar pago.
- Cancelar pago.
- Eliminar pago.

El procesamiento no depende de condicionales por texto. Cada método de pago tiene su propio procesador concreto y todos implementan la interfaz `ProcesadorPago`.

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
8. Procesamiento de Pagos
```

## Etapa 10 - Órdenes de compra y checkout integrado

Se integró el módulo de órdenes dentro del menú principal y se agregó el flujo de checkout.

Clases agregadas en `src/main/java/ecommerce/model`:

```text
DatosEnvio
```

Clases agregadas en `src/main/java/ecommerce/model/builder`:

```text
OrdenCompraBuilder
```

Clases agregadas en `src/main/java/ecommerce/service`:

```text
CarritoSesionService
CheckoutFacade
```

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
OrdenMenu
TipoEnvioSelector
EstadoOrdenSelector
```

También se ampliaron:

```text
ServiceFactory
CarritoMenu
MenuPrincipal
ConsolaUtils
OrdenService
```

Funcionalidades disponibles desde consola para órdenes:

- Confirmar compra desde el carrito activo.
- Procesar pago usando Strategy.
- Crear envío asociado.
- Generar orden de compra.
- Persistir orden e ítems.
- Descontar stock automáticamente.
- Vaciar el carrito luego de confirmar la compra.
- Buscar orden por número.
- Listar órdenes.
- Listar órdenes por cliente.
- Listar órdenes por estado.
- Actualizar estado de orden.
- Eliminar orden.

El flujo integrado queda así:

```text
Cliente activo
    ↓
Carrito con productos
    ↓
CheckoutFacade
    ↓
Procesamiento de pago
    ↓
Creación de envío
    ↓
Generación de orden
    ↓
Egreso de stock
    ↓
Carrito vacío
```

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
7. Órdenes de Compra
8. Procesamiento de Pagos
```

## Etapa 11 - Envíos y seguimiento de pedidos

Se integraron los módulos de gestión de envíos y seguimiento dentro del menú principal.

Clases agregadas en `src/main/java/ecommerce/model`:

```text
EnvioHistorialEstado
```

Clases agregadas en `src/main/java/ecommerce/service`:

```text
SeguimientoService
```

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
EnvioMenu
SeguimientoMenu
EstadoEnvioSelector
```

También se ampliaron:

```text
EnvioDAO
SQLiteEnvioDAO
EnvioService
ServiceFactory
MenuPrincipal
ConsolaUtils
schema.sql
```

Funcionalidades disponibles desde consola para envíos:

- Crear envío manual.
- Buscar envío por código de seguimiento.
- Listar envíos.
- Listar envíos por estado.
- Actualizar estado de envío.
- Consultar historial de estados.
- Consultar fecha estimada de entrega.
- Eliminar envío.

Funcionalidades disponibles desde consola para seguimiento:

- Consultar pedido por número de orden.
- Consultar envío por código de seguimiento.
- Consultar envío asociado a una orden.
- Consultar historial de envío por código.
- Consultar historial de envío por orden.
- Consultar fecha estimada por código.
- Consultar fecha estimada por orden.

Se agregó persistencia de historial de estados mediante la tabla:

```text
envio_historial_estados
```

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
7. Órdenes de Compra
8. Procesamiento de Pagos
9. Gestión de Envíos
10. Seguimiento de Pedidos
```

## Etapa 12 - Reclamos, devoluciones y calificaciones

Se completó el módulo de post compra dentro del menú principal.

Interfaces DAO agregadas:

```text
DevolucionDAO
CalificacionDAO
```

Implementaciones SQLite agregadas:

```text
SQLiteDevolucionDAO
SQLiteCalificacionDAO
```

Servicios agregados:

```text
DevolucionService
CalificacionService
```

Clases agregadas en `src/main/java/ecommerce/ui`:

```text
PostCompraMenu
EstadoReclamoSelector
EstadoDevolucionSelector
```

También se ampliaron:

```text
DAOFactory
SQLiteDAOFactory
ServiceFactory
MenuPrincipal
ConsolaUtils
schema.sql
```

Funcionalidades disponibles desde consola:

- Generar reclamos sobre órdenes existentes.
- Buscar reclamos.
- Listar reclamos.
- Listar reclamos por estado.
- Cambiar estado de reclamo.
- Eliminar reclamos.
- Solicitar devoluciones de productos comprados.
- Buscar devoluciones.
- Listar devoluciones.
- Listar devoluciones por cliente, producto o estado.
- Cambiar estado de devolución.
- Eliminar devoluciones.
- Calificar productos comprados.
- Buscar calificaciones.
- Listar calificaciones.
- Listar calificaciones por cliente o producto.
- Consultar promedio de calificaciones de un producto.
- Eliminar calificaciones.

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
7. Órdenes de Compra
8. Procesamiento de Pagos
9. Gestión de Envíos
10. Seguimiento de Pedidos
11. Reclamos y Devoluciones
```

## Etapa 13 - Reportes de gestión

Se integró el módulo de reportes dentro del menú principal.

Clase agregada en `src/main/java/ecommerce/ui`:

```text
ReporteMenu
```

También se amplió:

```text
MenuPrincipal
```

Reportes disponibles desde consola:

- Cantidad total de usuarios.
- Cantidad de clientes.
- Cantidad de productos.
- Productos por categoría.
- Productos sin stock.
- Productos más vendidos.
- Órdenes generadas.
- Órdenes por estado.
- Recaudación total.
- Recaudación por método de pago.
- Clientes con más compras.
- Reclamos abiertos.
- Reclamos resueltos.
- Envíos pendientes.
- Envíos entregados.
- Resumen general del sistema.

El menú principal ya permite acceder a:

```text
1. Gestión de Usuarios
2. Gestión de Roles
3. Gestión de Productos
4. Gestión de Categorías
5. Gestión de Inventario
6. Carrito de Compras
7. Órdenes de Compra
8. Procesamiento de Pagos
9. Gestión de Envíos
10. Seguimiento de Pedidos
11. Reclamos y Devoluciones
12. Reportes
```

## Etapa 14 - Integración final, autenticación y permisos

Se agregó control de acceso al sistema antes de ingresar al menú principal.

Clases agregadas:

```text
src/main/java/ecommerce/service/SesionUsuarioService.java
src/main/java/ecommerce/service/AutenticacionService.java
src/main/java/ecommerce/ui/AutenticacionMenu.java
```

También se actualizaron:

```text
AplicacionConsola
MenuPrincipal
ServiceFactory
ClienteSelector
CarritoMenu
OrdenMenu
PostCompraMenu
```

Funcionalidades agregadas:

- Creación automática del primer administrador si la base no tiene usuarios.
- Inicio de sesión mediante email y contraseña.
- Validación de usuario activo antes de operar el sistema.
- Control de acceso por rol desde el menú principal.
- Cierre de sesión al salir del menú principal.
- Uso automático del cliente de sesión en carrito, órdenes y post compra cuando el usuario logueado tiene rol `CLIENTE`.

Permisos aplicados en el menú principal:

```text
Administrador: usuarios, roles, productos, categorías, inventario, órdenes, pagos, envíos, seguimiento, post compra y reportes.
Cliente: carrito, órdenes, seguimiento y post compra.
Operador de Ventas: órdenes, pagos, seguimiento y post compra.
Responsable de Logística: envíos y seguimiento.
```

## Compilar el proyecto

Con Java 17:

```powershell
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName
javac -d out $files
java -cp out ecommerce.Main
```

## Inicializar SQLite

Para inicializar la base de datos se recomienda Maven, porque el driver SQLite se declara como dependencia en `pom.xml`.

```bash
mvn clean compile exec:java -Dexec.mainClass=ecommerce.database.DatabaseInitializerApp
```

Eso genera el archivo:

```text
database/ecommerce.db
```

## Ejecutar con Maven

```bash
mvn clean compile exec:java
```
