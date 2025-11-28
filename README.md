# Management Working Day API REST
<hr>
Está es una aplicación de gestoría de jornada de trabajo, desarrollada en:<br>
Java 21, Spring Boot 3.5.8, Maven, OpenApi 2.8.14 y Swagger, gestionado con la base de datos<br>
H2 Database además de Spring Data JPA<br>

Con esta aplicación se pueden realizar las siguientes tareas:<br>
<br>
**Empleados**
- Crear empleados.
- Ver todos los empleados.
- Editar empleador y asignarle un departamento en el proceso.
- Ver un solo empleado buscado por su ID.
- Hacer fichaje de entrada y salida de un empleado.
- Ver todos los fichajes de un empleado.
- Eliminar empleado.

**Departamento**
- Crear un departamento.
- Listar todos los departamentos.
- Buscar un departamento.
- Editar un departamento.
- Eliminar un departamento.

Para poder ver el proyecto debe clonar el repositorio con el siguiente comando:
```
git clone https://github.com/AlvLazCas06/Management-Working-Day-API-REST.git
```

Lo siguiente será abrir el proyecto en su IDE de uso particular:

**Eclipse/STS:**
- Abir editor de código.
- Realizar la siguiente ruta.
  - File -> import -> Maven -> Existing Maven Projects.
- Seleccionar la carpeta del proyecto y revisar que detecte bien el archivo pom.xml de Maven. 

**IntelliJ IDEA:**
- Abir editor de código.
- File -> open / File -> new project from existing version -> seleccionar que es un proyecto de maven.

**VS Code:**
- Abrir editor de código.
- File -> open folder -> seleccionar la carpeta del proyecto.
- Revisar que tenemos instaladas las extensiones de Java para VS Code.

# Ejecución del proyecto

**Eclipse/STS:** Para ello tenemos que hacer clic derecho sobre la carpeta del proyecto darle al apartado run as y seleccionamos Spring Boot Application.

**IntelliJ IDEA:**
- En caso de estar en la versión Ultimate al importar el proyecto ya se crea un botón de ejecución para una aplicación de Spring Boot.
- En caso de no tener la versión Ultimate tenemos que hacer lo siguiente:
  - Botón de Maven -> Carpeta Working Day -> plugins -> spring-boot -> spring-boot:run.

**VS Code:**
- Una vez instalados los plugins de Java ya cada vez que estemos en un proyecto de Java aparecera un botón para la ejecución.

Una vez tenemos el proyecto en ejecución tenemos que ir a la siguiente url en Postman:
```
http://localhost:8080/api/v1  
#/employee si quieres trabajar con empleados 
#/department si quieres trabajar con departamentos
```

Si quieres acceder a la documentacion de la API tienes que utilizar esta url en el navegador:
```
http://localhost:8080/swagger-ui.html
```