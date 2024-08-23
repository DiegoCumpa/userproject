
## Práctica: Desarrollo de Microservicio con Spring WebFlux y Arquitectura Hexagonal

Microservicio Java 17 Spring Boot, obtiene, guarda, actualiza y elimina datos de la entidad usuario. El servicio se conecta con una base de datos mongoDB, a la colección "usuarios". Adicionalmente cuenta con pruebas unitarias, manejo de errores y manejo de limite de solicitudes consecutivas para cada endpoint. 


<h1 align="center">userApi</h1>
<p align="center"> Diagrama de componentes</p>
<p align="center"><img src="https://i.ibb.co/J5cdTzZ/Diagrama-sin-t-tulo.jpg"/></p> 

Contenido:
---

- [Requerimientos](#guía-de-usuario)
- [Guía de instalación](#guía-de-instalación)
- [Endpoints](#endpoints)
- [Información adicional](#información-adicional)
- [Autor](#autores)

### Tecnologías usadas

- Spring Boot: ![version](https://img.shields.io/badge/version-3.2.4-blue)
- Spring WebFlux: ![version](https://img.shields.io/badge/version-6.1.5-blue)
- JUnit: ![version](https://img.shields.io/badge/version-5.10.2-blue)
- Resilience4J: ![version](https://img.shields.io/badge/version-2.2.0-blue)
- Java: ![version](https://img.shields.io/badge/version-17-blue)
- Mockito: ![version](https://img.shields.io/badge/version-5.11.0-blue)
- WebTestClient: ![version](https://img.shields.io/badge/version-3.2.0-blue)
- Jacoco: ![version](https://img.shields.io/badge/version-0.8.12-blue)

## Requerimientos
---
- JDK 17
- Maven 3
- Postman
- IntelliJ Idea Community Edition
- MongoDB Compass
- Git Bash


## Guía de instalación
---
Una vez teniendo instalado las herramientas mencionadas anteriormente, procedemos a realizar los siguientes pasos:


- Abrir Git Bash y clonar el repositorio.

        git clone https://github.com/DiegoCumpa/userproject.git

- Situarse en la rama "develop".

        git checkout develop.

- Abrir IntelliJ Idea Communiti Edition e importar el proyecto.
- Presionar ALT + F12 para abrir la consola.
- Compilar y descargar dependencias del proyecto.

        mvn clean install
- Desplegamos el proyecto de forma local desde la clase principal "UserApplication".
- Una vez desplegado, ejecutamos Postman.
- Importamos la colección CRUD USER.postman_collection

        File >  Import... > Files
- Procedemos a golpear cada ruta expuesta realizando las pruebas correspondientes.
- Una vez realizadas las pruebas, nos situamos en la consola y ejecutamos las pruebas unitarias.

        mvn test
- Procedemos a generar el sitio web para tener visibilidad de la cobertura de código.

        mvn site
- Ingresamos al siguiente directorio del proyecto:

        \user\target\site\jacoco\index.html
- Click derecho al archivo y abrir con tu navegador web de preferencia

        Open in > Browser
- Se abrirá una página web donde tendremos detallado la cobertura de código, teniendo visibilidad del proyecto tanto a nivel de clases como paquetes.

Conexión a la base de datos:
- Descargamos MongoDBCompass.

        https://downloads.mongodb.com/compass/mongodb-compass-1.42.5-win32-x64.exe
- Procedemos a instalar el programa.
- Abrimos MongoDBCompass una vez instalado.
- Click en "New Connection"
- Insertamos la siguiente cadena de conexión en el campo "Paste your connection string (SRV or Standard )".

        mongodb+srv://admin:aOJqU8Wy715MXgJd@cluster0.pc2et61.mongodb.net/dbsistema?authSource=admin&replicaSet=atlas-etruby-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true

- Esperamos y ya tendremos visualización de la base de datos "dbsistema".


## Endpoints
---
El microservicio consta de 4 endpoints:

GET /v1/users

POST /v1/users

PUT /v1/users/{{$id}}}

DELETE /v1/users/{{$id}}}

## Información adicional
---
- El microservicio se puede conectar a la base de datos desde cualquier lugar.
- El servicio tiene pruebas unitarias.
- El servicio tiene más de 85% en cobertura de código
- No puedes realizar solicitudes de forma seguid a cada endpoint(Regla de Circuit Breaker).
- El microservicio es completamente reactivo tanto como las pruebas, las entidads, el servidor de despliegue, la base de datos y la lógica de implementación.

## Autor
---
José Diego Cumpa Levano

Titulado de la carrera de computación e informática. responsable, con iniciativa, puntualidad y creatividad, asumo con seriedad los retos y metas que la organización me plantee, adaptabilidad al cambio, facilidad para trabajar en equipo, así como para resolver problemas eficientemente y lograr las metas de productividad trazadas por la empresa, me considero muy innovador, pregunto mucho, me encanta aprender, mi carrera me apasiona mucho, soy una persona bien práctica, positiva y persuasiva. Con interés en desarrollarme como desarrollador. Siempre trato de acoplar la tecnología a mi vida y con buen manejo del tiempo. Predisposición para todas las cosas.


