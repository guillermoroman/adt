# Enunciado del Ejercicio: Creación de un CRUD Básico con API REST para Gestión de Tareas  

#### Objetivo  
Desarrollar una API REST básica para la gestión de tareas, permitiendo crear, leer, actualizar y eliminar tareas almacenadas en una base de datos.  

#### Requisitos  

1. **Modelo de Tarea**:  
   - `title` (string, obligatorio): Título de la tarea.  
   - `description` (string, opcional): Descripción detallada de la tarea.  
   - `completed` (boolean, por defecto `false`): Indica si la tarea ha sido completada.  

2. **Endpoints REST**:  
   - **POST `/tasks`** → Crear una nueva tarea.  
   - **GET `/tasks`** → Listar todas las tareas.  
   - **GET `/tasks/{id}`** → Obtener una tarea por su ID.  
   - **PUT `/tasks/{id}`** → Actualizar una tarea existente.  
   - **DELETE `/tasks/{id}`** → Eliminar una tarea.  

3. **Requisitos Técnicos**:  
   - Utilizar una base de datos como **H2, SQLite, MariaDB**.  
   - Formato de datos en **JSON**.  

#### Extras (Opcional)  
- Validaciones en los datos de entrada.  
- Mensajes de error adecuados en caso de fallo.  
- Posibilidad de filtrar tareas completadas o pendientes.  


## Parte 2
Nuevo endpoint:
   - **GET `/tasks/filter`** → Obtener una tarea por su ID.
   - Ejemplo: tasks/filter?completed=true



##
🚀 **Más adelante se añadirá la gestión de fechas de vencimiento (`due_date`) y asignación de usuarios (`user`) a cada tarea.**
