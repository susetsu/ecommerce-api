# 🛒 Ecommerce API

API RESTful para una plataforma de comercio electrónico desarrollada con Spring Boot. Esta API permite gestionar productos, usuarios, pedidos y autenticación mediante JWT, proporcionando una solución robusta y escalable para tiendas en línea.

## 🚀 Características

- **Gestión de productos**: CRUD completo con paginación y filtrado por disponibilidad.
- **Autenticación y autorización**: Registro e inicio de sesión de usuarios con validaciones y generación de tokens JWT.
- **Gestión de pedidos**: Creación, consulta y eliminación de pedidos asociados a usuarios autenticados.
- **Validaciones**: Validación de entradas utilizando `jakarta.validation` y mensajes personalizados en español.
- **Documentación interactiva**: Integración con Swagger UI para explorar y probar los endpoints.

## 📦 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (JSON Web Tokens)
- Swagger / OpenAPI
- H2 (base de datos en memoria para desarrollo)

## 📄 Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva de Swagger en:

- http://localhost:8081/swagger-ui/index.html


## 🛠️ Instalación y ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.8 o superior

### Pasos para ejecutar la aplicación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/susetsu/ecommerce-api.git
   cd ecommerce-api
   ```
2. Compila y ejecuta la aplicación:
   ```mvn spring-boot:run ```

## 🗂️ Estructura del proyecto
```bash
src/
├── main/
│   ├── java/
│   │   └── com.avilatek.ecommerce.ecommerce_api/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/
│   │       └── util/
│   └── resources/
│       ├── application.properties
└── test/
    └── java/
        └── com.avilatek.ecommerce.ecommerce_api/
```
## 🔐 Seguridad

 - Autenticación basada en JWT.
 - Validación de contraseñas fuertes y correos electrónicos válidos.
 - Protección de endpoints sensibles mediante roles y autenticación.


## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT.