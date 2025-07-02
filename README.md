# Gestión y Administración de Ventas - Microservicio

## Descripción
Este microservicio está desarrollado con Spring Boot y Maven para gestionar el proceso de ventas en una aplicación, incluyendo detalles de las transacciones y cupones asociados. Las operaciones incluyen la creación, actualización, eliminación y consulta de ventas, junto con sus datos relacionados, como detalles de venta y cupones. El diseño sigue una arquitectura en capas: Model, Repository, Service y Controller, exponiendo funcionalidades mediante APIs REST.

## Tecnologías usadas

- Java 17  
- Spring Boot  
- Oracle Database  
- Maven  
- Lombok  
- Postman (para pruebas de endpoints)
- Swagger (SpringDoc OpenAPI) 

## Dependencias incluidas (`pom.xml`)

- `spring-boot-starter-web`  
- `spring-boot-starter-data-jpa`  
- `lombok`  
- `ojdbc8` (Oracle JDBC Driver)  
- `oraclepki`, `osdt_core`, `osdt_cert` (Oracle Wallet Security)  
- `springdoc-openapi-starter-webmvc-ui` (Swagger UI / documentación de API)

## Estructura del proyecto
# Modelos:
Definen las entidades principales: Venta, Detalle_Venta, Cupon. Estas clases incluyen atributos específicos y relaciones como @OneToOne y @ManyToOne.

**Repository:** 
La entidad  venta tiene su correspondiente repositorio que extiende JpaRepository, permitiendo operaciones CRUD y consultas personalizadas para buscar ventas por ID, listar todas las ventas, etc.

**Service:**  
Contienen la lógica de negocio: Validaciones para garantizar datos consistentes. Métodos para guardar, actualizar y eliminar ventas.


**Controller:**  
Define los endpoints REST para exponer las operaciones CRUD. Se implementan validaciones en las solicitudes y se manejan respuestas con códigos HTTP adecuados (ejemplo: 200, 201, 400, 404).

## Relación entre entidades

`Venta` tiene una relación @OneToOne con Detalle_Venta, permitiendo detallar productos adquiridos.

`Venta` tiene una relación @ManyToOne con Cupon, permitiendo asociar descuentos opcionales.

## Funcionalidades principales

- Crear, actualizar, eliminar y listar Ventas.
- Búsquedas por atributos relevantes como ejemplo por ID o Productos.
- Validaciones básicas en las solicitudes para  evitar duplicados.

## Configuración importante

- Configuración en `application.properties` para conexión a base de datos Oracle con parámetros de Wallet.
- Hibernate configurado para actualizar el esquema automáticamente (`spring.jpa.hibernate.ddl-auto=update`).

