# Student Management System

El sistema de gestión de estudiantes es una aplicación web diseñada para ayudar a las instituciones educativas a administrar sus datos de estudiantes, incluidos registros de estudiantes, asistencias, calificaciones y más. Desarrollado con tecnologías modernas como Spring Boot y Spring Security, el sistema ofrece una solución segura y fácil de usar para las necesidades de gestión académica.

## Tecnologías Utilizadas

El sistema se basa en las siguientes tecnologías:

- **Spring Boot:** Un marco de desarrollo de aplicaciones Java que simplifica la creación de aplicaciones Spring.
- **Spring Security:** Proporciona autenticación y autorización robustas para asegurar la aplicación.
- **JWT (JSON Web Token):** Un estándar de token abierto que permite la transferencia segura de información entre partes.
- **Hibernate:** Un marco ORM (Mapeo Objeto-Relacional) que simplifica el acceso a la base de datos relacional.
- **MySQL:** Un sistema de gestión de bases de datos relacional de código abierto.
- **Maven:** Una herramienta de gestión de proyectos y construcción de software.

## Estructura del Proyecto

El proyecto sigue una arquitectura de capas que separa claramente las responsabilidades y promueve una base de código limpia y mantenible:

- **Capa de Presentación:** Esta capa contiene los controladores REST que manejan las solicitudes HTTP entrantes y envían respuestas al cliente.
- **Capa de Aplicación:** Aquí se encuentran los servicios de aplicación que implementan la lógica de negocio de la aplicación.
- **Capa de Dominio:** Define las entidades del dominio y las reglas de negocio del sistema.
- **Capa de Infraestructura:** Proporciona la integración con la base de datos y otros servicios externos.
