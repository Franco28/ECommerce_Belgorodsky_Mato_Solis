# E-Commerce Final - Programación Avanzada

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

## Decisión técnica

Las excepciones personalizadas extienden de `EcommerceException`, que a su vez extiende de `RuntimeException`.

Esto permite mantener los modelos limpios y evita llenar constructores, setters y métodos simples con firmas `throws`.

## Ejecutar / Compilar Proyecto

Desde PowerShell:

```powershell
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName
javac -d out $files
java -cp out ecommerce.Main
```
