# **Documento Maestro del Proyecto: HexaLink (MVP y Fase 2)**


## **1. Visión General y Objetivos Estratégicos**

### **1.1. Propósito del Documento**
Este documento es la única fuente de verdad (Single Source of Truth) para el desarrollo de la plataforma HexaLink. Define la arquitectura, la pila tecnológica, los procesos de desarrollo y el alcance funcional para el **Producto Mínimo Viable (MVP)** y traza el camino para la **Fase 2**.

### **1.2. Objetivo de Negocio**
Lanzar progresivamente una plataforma unificada de capital humano que automatice procesos, mejore la visibilidad de los datos y ofrezca una experiencia de usuario superior tanto para colaboradores como para el equipo de RRHH.

### **1.3. Objetivos Técnicos**
*   **Validar la Arquitectura Polyglot y Hexagonal:** Demostrar que los servicios construidos en Java y C# pueden comunicarse eficientemente y que la lógica de negocio está correctamente aislada.
*   **Establecer un Flujo de Trabajo Profesional:** Implementar desde el inicio prácticas de Gitflow, CI/CD y pruebas unitarias para garantizar la calidad y agilidad del desarrollo.
*   **Sentar Bases Escalables:** Construir sobre un esquema de datos completo y una arquitectura limpia para facilitar el crecimiento futuro.

## **2. Composición del Equipo y Roles**

| Rol | Tecnología Primaria | Responsabilidad Principal |
| :--- | :--- | :--- |
| **Backend 1** | Java (Spring Boot) | Microservicio de Recruiting |
| **Backend 2 (Equipo)** | C# (.NET) | Microservicio de Personal/Trámites |
| **Frontend** | Next.js (TypeScript) | Interfaz de Usuario, Consumo de APIs |
| **Analista de Datos** | SQL/NoSQL | Scripts de Base de Datos, Modelo de Datos |

## **3. Arquitectura y Pila Tecnológica**

### **3.1. Principios de Arquitectura**
*   **Microservicios:** Dominios de negocio desacoplados, desplegables y escalables de forma independiente.
*   **Arquitectura Hexagonal (Puertos y Adaptadores):** Aislamiento total del núcleo de la lógica de negocio de la infraestructura externa (APIs, BBDD).
*   **Polyglot:** Uso de Java/Spring Boot para el servicio de Recruiting y C#/.NET para el servicio de Personal/Trámites.

### **3.2. Diagrama de Conexión de Componentes**
```
            +------------------+
            |      Usuario     |
            +--------+---------+
                     |
            +--------v---------+
            |   Frontend       |
            |   (Next.js)      |
            +--------+---------+
                     |
            +--------v---------+
            |   API Gateway    |
            +--------+---------+
                     |
         +-----------+-----------+
         |                       |
+--------v---------+     +-------v----------+
|  Microservicio   |     |  Microservicio   |
|   Recruiting     |<--->|   Personal       |
|  (Java/SB)       |     |   (C#/.NET)      |
+--------+---------+     +--------+---------+
         |                       |
+--------v---------+     +-------v----------+
|   Base de Datos  |     |   Base de Datos  |
|   (PostgreSQL)   |     |   (SQL Server)   |
+------------------+     +------------------+
```

### **3.3. Pila Tecnológica Detallada (Stack)**

| Componente | Lenguaje/Framework | Base de Datos | Pruebas Unitarias |
| :--- | :--- | :--- | :--- |
| **Frontend** | TypeScript, Next.js, React | N/A | Jest, React Testing Library |
| **Backend (Recruiting)**| Java 17+, Spring Boot 3 | PostgreSQL | JUnit 5, Mockito |
| **Backend (Personal)** | C# 11+, .NET 7/8 | SQL Server | xUnit, Moq |

## **4. Fundamentos de Desarrollo, Calidad y Operaciones (DevOps)**

Estos procesos son **obligatorios** para el MVP y no son negociables.

### **4.1. Estrategia de Ramificación (Gitflow)**
Se utilizará un flujo de trabajo Gitflow estricto para mantener la integridad del código.
*   **`main`**: Refleja el código en producción. Solo acepta merges desde `develop` para nuevos lanzamientos.
*   **`develop`**: Rama de integración principal. Contiene las últimas funcionalidades completadas.
*   **`feature/nombre-feature`**: Cada nueva tarea se desarrolla en su propia rama, creada a partir de `develop`.
*   **Pull Requests (PRs)**: Todo el código debe ser fusionado a `develop` a través de un PR. Se requiere la aprobación de al menos un miembro del equipo y que todos los chequeos automáticos (pruebas, linting) pasen.

### **4.2. Gestión de Tareas**
Se utilizará una organización de **GitHub (o Azure DevOps)** para la gestión del proyecto.
*   **Repositorios:** Se creará un repositorio por cada microservicio y uno para el frontend.
*   **Tablero de Proyecto (Kanban):** Se configurará un tablero con las columnas: `Backlog`, `To Do`, `In Progress`, `In Review`, `Done`.
*   **Tareas (Issues):** Cada tarea del plan de ejecución se creará como un "Issue" asignado a un responsable.

### **4.3. Calidad y Pruebas Unitarias**
La calidad es un pilar del MVP.
*   **Cobertura de Pruebas:** Es **mandatorio** que toda nueva lógica de negocio (casos de uso, servicios de dominio) esté cubierta por pruebas unitarias.
*   **Ejecución Automática:** Las pruebas se ejecutarán automáticamente en cada `push` y antes de permitir el merge de un PR. Un build fallido bloqueará la fusión.

### **4.4. Integración y Despliegue Continuo (CI/CD)**
Se implementará un pipeline de CI/CD básico desde el inicio utilizando **GitHub Actions**.
*   **Trigger:** El pipeline se activará en cada merge a la rama `develop`.
*   **Pasos del Pipeline:**
    1.  **Build:** Compilar el código fuente y ejecutar linters.
    2.  **Test:** Ejecutar la suite completa de pruebas unitarias.
    3.  **Package:** Crear los artefactos desplegables (imágenes de Docker para cada servicio).
    4.  **Push:** Subir las imágenes a un registro de contenedores (ej. GitHub Container Registry, Docker Hub).
    5.  **Deploy:** Desplegar automáticamente las nuevas imágenes en el entorno de pruebas/demo.

### **4.5. Fundamentos de Seguridad**
*   **Gestión de Secretos:** Está **prohibido** commitear contraseñas, claves de API o connection strings. Se utilizarán **GitHub Secrets** para gestionar las variables de entorno y se inyectarán en el pipeline de despliegue.
*   **Análisis de Dependencias:** Se habilitará **Dependabot** en todos los repositorios para alertar y crear PRs automáticamente para actualizar dependencias con vulnerabilidades conocidas.
*   **CORS:** Los backends se configurarán para aceptar peticiones únicamente desde el dominio del frontend desplegado.

---

## **5. Fase 1: El Producto Mínimo Viable (MVP)**
*(Duración: 1.5 semanas)*

### **5.1. Objetivo Específico del MVP**
Demostrar un flujo funcional de extremo a extremo que valide la arquitectura y la comunicación entre servicios, desplegado automáticamente y construido sobre un esquema de base de datos completo y prácticas de desarrollo robustas.

### **5.2. Modelo de Datos del MVP (Esquema Completo)**
Se implementará el esquema de base de datos completo del ERD proporcionado desde el inicio a través de scripts de migración (EF Core Migrations para .NET, Flyway/Liquibase para Java).

*   **Microservicio de Personal (C#):** Creación de todas las tablas (`employees`, `requests`, `approvals`, etc.).
*   **Microservicio de Recruiting (Java):** Creación de sus tablas (`vacancies`, `candidates`, `interview_schedules`, etc.).

### **5.3. Alcance Funcional y Endpoints del MVP (Capa de Lógica Mínima)**

*   **Microservicio de Personal (C#):**
    *   `POST /employees`
    *   `GET /employees`
    *   `POST /vacation-requests`
*   **Microservicio de Recruiting (Java):**
    *   `POST /vacancies`
    *   `GET /vacancies`
    *   `POST /candidates`
*   **Frontend (Next.js):**
    *   Página `/vacantes` (consume servicio Java).
    *   Página `/empleados` (consume servicio C#).

### **5.4. Funcionalidad Cruzada Crítica**
*   **`GET /recruiting/internal-search`**: Endpoint en **Java** que llama al `GET /employees` del servicio **C#**.

---

## **6. Fase 2: Consolidación y Expansión Funcional**
*(Roadmap Post-MVP)*

### **6.1. Objetivos de la Fase 2**
Transformar el prototipo técnico en una herramienta funcional y segura que pueda ser utilizada en un piloto interno, enfocándose en un flujo de negocio completo (ej. solicitud y aprobación de vacaciones).

### **6.2. Funcionalidades Clave de la Fase 2**
*   **Seguridad y Autenticación:** Implementar un flujo de login con JWT. Proteger todos los endpoints.
*   **Flujo de Aprobación Completo:** Desarrollar la lógica y los endpoints para que un manager pueda aprobar/rechazar solicitudes.
*   **CRUD Completo para Empleados:** Habilitar la edición y el borrado lógico de empleados.
*   **Carga de CVs:** Implementar la subida de archivos en el microservicio de Recruiting.
*   **Notificaciones Básicas:** Generar notificaciones en la base de datos tras acciones clave.
*   **Mejoras de UI/UX en Frontend:** Implementar un panel para managers, formularios de edición y un sistema de notificaciones en la interfaz.

## **7. Definición de "Hecho" (Definition of Done)**

Una tarea se considera **"Hecha"** únicamente cuando cumple con **todos** los siguientes criterios:
1.  El código implementa la funcionalidad requerida.
2.  El código está cubierto por **pruebas unitarias** que validan la lógica.
3.  El código sigue las guías de estilo y no genera advertencias del linter.
4.  El **pipeline de CI/CD se ejecuta con éxito** (build y tests pasan).
5.  El código ha sido revisado y **aprobado en un Pull Request** por al menos un miembro del equipo.
6.  El PR ha sido fusionado a la rama `develop`.
7.  La funcionalidad ha sido verificada en el entorno de demo desplegado.