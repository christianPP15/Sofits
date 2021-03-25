# Sofits

## Aplicación para el intercambio de libros.


### Frontend con android haciendo uso de dagger y coil.
### Backend con kotlin, spring boot y spring security subiendo imagenes a imgur. 

A nivel de backend:
1. Todas las peticiones que devuelvan más de un elemento admiten paginación
2. Se realizan filtros utilizando JpaSpecification 
3. La seguridad está implementada mediante tokens JWT 

A nivel de frontend: 
1. Se utiliza dagger para la generación de dependencias.
2. Se utiliza coil para la carga dinámica de imagenes
3. Se utiliza retrofit para realizar peticiones a la API

### Funcionalidades

La app ***Sofits*** cuenta con varias funcionalidades dependiendo del tipo de usuario que seas:

- Usuarios Administradores:
1. Agregar nuevos autores.
2. Eliminar publicaciones de usuarios.
3. Agregar nuevos libros a los autores.

- Usuarios:
1. Ver la listas de los autores.
2. Ver un detalle de un autor con su información y sus libros.
3. Ver las publicaciones del resto de usuarios sobre un libro.
4. Ver mi perfil con los libros que he publicado.
5. Ver mi valoración media.
7. Agregar nuevas publicaciones.
8. Filtrar los libros por géneros, autor y títulos.
9. Loguearse en la aplicación.

### EndPoints

Las peticiones que puede aceptar la API son las siguientes:

##### *Gestión de usuarios*

- `/auth/register` (POST) - Registrar un usuario

- `/auth/login` (POST) - Acceder como un usuario registrado

##### *Gestión de autores*

- `/autores/` (GET) - Obtener todos los autores
- `/autores/{id Autor}` (GET) - Obtener un autor en base a su id
- `/autores/` (POST) - Agregar un nuevo autor
- `/autores/{Id autor}` (PUT) - Editar un autor en base a su id
- `/autores/{Id autor}` (DELETE) - Eliminar un autor en base a su id

##### *Gestión de libros*

- `/libro/?titulo=&genero=&autor` (GET) Obtener una lista de libros filtrando por el título, el nombre del autor y el género
- `/libro/{id libro}` (GET) Obtiene un libro en base a su id
- `/libro/{id Autor}` (POST) Agrega un libro a un autor
- `/libro/{id libro}` (PUT) Edita la información de un libro
- `/libro/{id Libro}` (DELETE) Elimina un libro en base a su id
- `/libro/{id libro}/{id autor}` (POST) Agrega un género a un libro
- `/libro/{id libro}/{id genero}` (DELETE) Elimina un género de un libro

##### *Gestión de géneros literarios*

- `/generos/` (GET) Muestra todos los géneros disponibles
- `/generos/{id}` (GET) Obtiene un género en base a su id
- `/generos/` (POST) Agrega un género
- `/generos/{id}` (DELETE) Elimina un género en base a su id

