# Spring Edu Manager

Proyecto Spring Boot con enfoque progresivo de formacion:
- MVC web con Thymeleaf y Bootstrap 5.3
- Persistencia con Spring Data JPA (H2/MySQL)
- Seguridad con Spring Security (roles ADMIN/USER)
- API REST CRUD para interoperabilidad entre sistemas
- Validaciones de entrada y manejo global de errores REST
- Documentacion interactiva con Swagger UI

## Stack Tecnologico

- Java 17+
- Spring Boot 3.5.x
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- Bootstrap 5.3
- H2 Database (por defecto)
- Maven Wrapper

## Arquitectura

Estructura por capas:
- `controller`: controladores MVC para vistas web
- `controller/api`: controladores REST JSON
- `service`: logica de negocio
- `repository`: acceso a datos con JpaRepository
- `model`: entidades JPA
- `dto`: contratos de entrada/salida para API REST
- `bootstrap`: carga de datos iniciales
- `client`: cliente de prueba con RestTemplate (opcional)

## Seguridad

Roles:
- ADMIN: acceso total web y REST
- USER: acceso a consulta y gestion de estudiantes

Credenciales de demo (configuradas en `application.properties`):
- ADMIN: `admin` / `admin123`
- USER: `usuario` / `user123`

Reglas clave:
- Solo ADMIN puede crear cursos desde la web (`/cursos/nuevo`, `POST /cursos`)
- REST API:
  - GET/POST/PUT: ADMIN o USER
  - DELETE: solo ADMIN

## Ejecucion Local

### 1) Compilar y testear

```bash
./mvnw clean install
```

En Windows (PowerShell):

```powershell
.\mvnw.cmd clean install
```

### 2) Levantar aplicacion

```powershell
.\mvnw.cmd spring-boot:run
```

### 2.1) Generar JavaDocs

```powershell
.\mvnw.cmd javadoc:javadoc
```

La documentacion se genera en:

- `target/reports/apidocs/index.html`

Para abrirla en Windows:

```powershell
Start-Process .\target\reports\apidocs\index.html
```

## Guia de instalacion para el equipo

Si tus companeros estan comenzando, comparteles este archivo:

- `GUIA-INSTALACION.md`

### 3) URLs importantes

- Login: http://localhost:8080/login
- Estudiantes (web): http://localhost:8080/estudiantes
- Cursos (web): http://localhost:8080/cursos
- Demo Frontend consumiendo API: http://localhost:8080/interoperabilidad
- H2 Console: http://localhost:8080/h2-console
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## API REST (JSON)

Base URL: `http://localhost:8080/api`

### Cursos

- GET `/api/cursos`
- GET `/api/cursos/{id}`
- POST `/api/cursos`
- PUT `/api/cursos/{id}`
- DELETE `/api/cursos/{id}`

Body ejemplo POST/PUT:

```json
{
  "nombre": "Arquitectura de Software",
  "descripcion": "Patrones, diseño y buenas practicas"
}
```

### Estudiantes

- GET `/api/estudiantes`
- GET `/api/estudiantes/{id}`
- POST `/api/estudiantes`
- PUT `/api/estudiantes/{id}`
- DELETE `/api/estudiantes/{id}`

Body ejemplo POST/PUT:

```json
{
  "nombre": "Laura Perez",
  "email": "laura.perez@example.com",
  "cursoId": 1
}
```

## Pruebas con curl

Autenticacion Basic con usuario ADMIN:

```bash
curl -u admin:admin123 http://localhost:8080/api/cursos
```

Crear curso:

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/cursos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"DevOps","descripcion":"CI/CD y automatizacion"}'
```

Actualizar estudiante:

```bash
curl -u usuario:user123 -X PUT http://localhost:8080/api/estudiantes/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Torres","email":"ana.actualizada@example.com","cursoId":2}'
```

Eliminar curso (solo ADMIN):

```bash
curl -u admin:admin123 -X DELETE http://localhost:8080/api/cursos/2
```

## Pruebas con Postman

1. Crear una coleccion con variable `baseUrl = http://localhost:8080`
2. En Authorization seleccionar Basic Auth
3. Probar con:
   - ADMIN para operaciones completas
   - USER para consultar y modificar recursos permitidos
4. Verificar respuestas HTTP:
   - 200 OK / 201 Created / 204 No Content
   - 403 Forbidden cuando el rol no tiene permiso
   - 404 Not Found cuando no existe el recurso

## Uso de la API desde Frontend

La pantalla `/interoperabilidad` demuestra consumo real con `fetch` sobre:
- GET `/api/cursos`
- GET `/api/estudiantes`
- POST `/api/cursos`
- POST `/api/estudiantes`

Beneficios para la demo:
- Muestra interoperabilidad sin herramientas externas
- Visualiza JSON en tiempo real
- Permite crear datos desde UI y ver reflejo inmediato en la API

## Datos Iniciales

Al iniciar la aplicacion se cargan datos de ejemplo (si la BD esta vacia):
- Cursos: Java Backend, Frontend Web
- Estudiantes: Ana Torres, Carlos Silva

## Validacion de Consumo Externo con RestTemplate

Existe un cliente de demostracion opcional:
- Clase: `RestTemplateDemoClient`
- Propiedad: `app.rest.client.enabled`

Para activarlo:

```properties
app.rest.client.enabled=true
```

Al iniciar, realiza una llamada GET a `/api/cursos` y registra el resultado en logs.

## Manejo de errores REST

Se implemento `@RestControllerAdvice` para respuestas uniformes de error JSON:
- Errores de validacion de campos (400)
- JSON invalido o mal formado (400)
- Fallback de error interno (500)

## Subida a GitHub

Comandos sugeridos (PowerShell):

```powershell
git init
git add .
git commit -m "Proyecto final Spring Edu Manager con MVC, seguridad y API REST"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPO.git
git push -u origin main
```

## Proximas Mejoras Recomendadas

- JWT para endpoints REST (plus de seguridad)
- Pipeline CI para build y tests en GitHub Actions
