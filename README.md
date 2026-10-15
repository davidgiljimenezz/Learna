# Learna
Plataforma web de cursos online desarrollada con Spring Boot, Thymeleaf y MySQL.

## Estructura principal

- `src/main/java`: código Java organizado por responsabilidad.
- `src/main/resources/templates`: páginas HTML procesadas por Thymeleaf.
- `src/main/resources/static/css`: estilos globales y estilos específicos por página.
- `src/main/resources/static/js`: comportamiento global y scripts específicos por página.
- `src/main/resources/static/images`: imágenes agrupadas por finalidad.
- `src/test/java`: pruebas automatizadas.

## Ejecutar el proyecto

Antes de iniciar la aplicación, configura las credenciales de MySQL como variables de entorno. En PowerShell:

```powershell
$env:DB_USERNAME="tu_usuario"
$env:DB_PASSWORD="tu_contraseña"
```

Estas variables solo duran mientras permanezca abierta esa ventana de PowerShell y no se guardan en Git.

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Después, abre `http://localhost:8080` en el navegador.

Puedes consultar [`.env.example`](.env.example) para conocer los nombres de las variables necesarias. Spring Boot no carga ese archivo automáticamente; sirve únicamente como referencia y nunca debe contener credenciales reales.

## Ejecutar las pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas usan H2 en memoria y no necesitan MySQL ni credenciales. Comprueban los formularios, la autenticación, el catálogo y las inscripciones con páginas reales de Thymeleaf.

## Funciones disponibles

- Registro con validación, email único y contraseñas almacenadas como hash PBKDF2.
- Inicio y cierre de sesión con Spring Security y protección CSRF en los formularios.
- Catálogo y ficha de cursos publicados desde MySQL.
- Inscripción del alumno conectado, sin duplicados, y página privada `/mis-cursos`.
- Contacto con validación, fecha, estado de lectura y límite de 4000 caracteres.

Hibernate crea las tablas `usuarios`, `cursos` e `inscripciones` al arrancar con la configuración de desarrollo actual. El catálogo empieza vacío. Para probarlo con un curso, ejecuta manualmente `docs/curso-ejemplo.sql` en tu base de desarrollo; no se insertan cursos automáticamente. Los cursos se gestionan por ahora desde la base de datos, sin panel de administración.

Para comprobar la web sin las credenciales de MySQL, puedes arrancar una vista de prueba en `http://localhost:8081`:

```powershell
.\mvnw.cmd spring-boot:test-run '-Dspring-boot.run.arguments=--server.port=8081 --spring.profiles.active=preview'
```

Esta vista usa la base temporal de pruebas e incluye tres cursos ficticios: al detenerla se pierden los registros e inscripciones creados allí. El perfil `preview` y sus cursos solo están en los recursos de pruebas, no se incluyen en la aplicación de producción.

El alcance actual incluye cuentas, catálogo e inscripciones. Los contenidos de las lecciones, pagos, recuperación de contraseña y administración quedan para ampliaciones posteriores.
