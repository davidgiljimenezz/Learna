# Academy David

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
