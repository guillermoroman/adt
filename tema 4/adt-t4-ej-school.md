
### **Ejercicio: Sistema de Gestión Escolar**

[Solución](https://github.com/guillermoroman/adt-t4-school-management-clase)

Eres responsable de desarrollar un sistema de gestión escolar utilizando Hibernate. Este sistema debe modelar las siguientes entidades y relaciones:

#### **Entidades principales:**

1. **Escuela (`School`)**:
   - Representa una institución educativa.
   - Cada escuela tiene un director (relación One-To-One).
   - Cada escuela tiene una lista de aulas (relación One-To-Many).

2. **Director (`Principal`)**:
   - Cada director está asociado únicamente a una escuela.

3. **Aula (`Classroom`)**:
   - Cada aula pertenece a una única escuela (relación Many-To-One).
   - Cada aula puede estar asignada a múltiples asignaturas (relación Many-To-Many).

4. **Asignatura (`Subject`)**:
   - Cada asignatura puede ser impartida en varias aulas (relación Many-To-Many).

#### **Requisitos del sistema**:

1. Implementar las entidades con las relaciones adecuadas:
   - **One-To-One** entre `School` y `Principal`.
   - **One-To-Many** entre `School` y `Classroom`.
   - **Many-To-Many** entre `Classroom` y `Subject`.

2. Crear un programa que permita:
   - Añadir una nueva escuela con su director y varias aulas.
   - Asignar asignaturas a las aulas.
   - Consultar una escuela y listar sus aulas, incluyendo las asignaturas impartidas en cada aula.

3. Implementar `FetchType` para:
   - Utilizar **`EAGER`** en la relación One-To-One entre `School` y `Principal`.
   - Utilizar **`LAZY`** en la relación One-To-Many entre `School` y `Classroom`.
   - Utilizar **`LAZY`** en la relación Many-To-Many entre `Classroom` y `Subject`.

4. Escribir un conjunto de pruebas con JUnit para:
   - Crear entidades y verificar que las relaciones se guardan correctamente.
   - Probar el comportamiento de **lazy loading** y **eager loading** al consultar los datos.

---

### **Relaciones esperadas**

1. **One-To-One**:
   - Una escuela tiene un director, y cada director pertenece a una sola escuela.
   
   Ejemplo: La escuela "Hogwarts" tiene como director a "Albus Dumbledore".

2. **One-To-Many / Many-To-One**:
   - Una escuela puede tener varias aulas, pero cada aula pertenece a una sola escuela.

   Ejemplo: La escuela "Hogwarts" tiene las aulas "101", "102", y "103".

3. **Many-To-Many**:
   - Una asignatura puede ser impartida en múltiples aulas, y un aula puede tener varias asignaturas.

   Ejemplo: El aula "101" imparte las asignaturas "Matemáticas" y "Historia", mientras que la asignatura "Matemáticas" también se imparte en el aula "102".

---

### **Modelado sugerido**

1. **Entidad `School`**:
   - Campos: `id`, `name`, relación con `Principal`, relación con `Classroom`.

2. **Entidad `Principal`**:
   - Campos: `id`, `name`, relación con `School`.

3. **Entidad `Classroom`**:
   - Campos: `id`, `name`, relación con `School`, relación con `Subject`.

4. **Entidad `Subject`**:
   - Campos: `id`, `name`, relación con `Classroom`.

---

### **Ejemplo de funcionalidad esperada**

1. Crear una escuela "Hogwarts" con el director "Albus Dumbledore" y las aulas:
   - Aula "101"
   - Aula "102"

2. Asignar asignaturas:
   - Aula "101": "Matemáticas", "Historia"
   - Aula "102": "Ciencias", "Matemáticas"

3. Consultar la escuela "Hogwarts" y listar:
   - Director: "Albus Dumbledore"
   - Aulas:
     - "101" (Asignaturas: "Matemáticas", "Historia")
     - "102" (Asignaturas: "Ciencias", "Matemáticas")
