# Guia de instalacion para el equipo (paso a paso)

Esta guia esta pensada para companeros que recien comienzan. No necesitas experiencia previa con GitHub para ejecutar el proyecto.

## 1. Que necesitas instalar

1. Java JDK 17 o superior
2. Git (opcional, pero recomendado)
3. Visual Studio Code (recomendado) o IntelliJ
4. Conexion a internet (la primera vez, Maven descarga dependencias)

## 2. Verificar instalaciones

En PowerShell, ejecuta:

```powershell
java -version
```

Debes ver Java 17 o superior.

Si instalaste Git, verifica:

```powershell
git --version
```

## 3. Obtener el proyecto

### Opcion A (recomendada): con Git

```powershell
git clone https://github.com/gipsy-yuilet-dev/Spring-Edu-Manager.git
cd Spring-Edu-Manager
```

### Opcion B: sin GitHub cuenta

1. Abre el repositorio en el navegador.
2. Haz clic en "Code".
3. Selecciona "Download ZIP".
4. Descomprime la carpeta.
5. Abre la carpeta del proyecto en VS Code.

## 4. Compilar y probar

En la raiz del proyecto ejecuta:

```powershell
.\mvnw.cmd clean install
```

Si termina con BUILD SUCCESS, todo esta correcto.

## 5. Ejecutar la aplicacion

```powershell
.\mvnw.cmd spring-boot:run
```

Cuando veas que arranco en puerto 8080, abre estas rutas:

1. Login: http://localhost:8080/login
2. Estudiantes: http://localhost:8080/estudiantes
3. Cursos: http://localhost:8080/cursos
4. Demo frontend consumiendo API: http://localhost:8080/interoperabilidad
5. Swagger (documentacion API): http://localhost:8080/swagger-ui/index.html
6. H2 Console: http://localhost:8080/h2-console

## 6. Credenciales de acceso

- ADMIN: `admin` / `admin123`
- USER: `usuario` / `user123`

## 7. Generar JavaDocs

```powershell
.\mvnw.cmd javadoc:javadoc
Start-Process .\target\reports\apidocs\index.html
```

## 8. Problemas comunes y solucion

1. Error "java no se reconoce": Java no esta instalado o no esta en PATH.
2. Puerto 8080 ocupado: cierra la app que usa ese puerto o cambia `server.port`.
3. Maven tarda mucho al inicio: es normal la primera vez por descarga de dependencias.
4. No abre Swagger: verifica que la app este corriendo y usa la ruta exacta `/swagger-ui/index.html`.

## 9. Si no tienes cuenta GitHub

Puedes trabajar localmente con ZIP sin problema. La cuenta de GitHub solo es necesaria para subir cambios al repositorio remoto.

## 10. Recomendacion para el equipo

Usen todos la misma version de Java (17+) para evitar errores de compatibilidad.
