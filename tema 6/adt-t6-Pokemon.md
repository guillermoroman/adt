## Consumir API de Pokemon desde Unity

En esta actividad, queremos hacer pequeña aplicación con Unity que realice una consulta a la api Pokeapi.co sobre un Pokemon en concreto que identificaremos por nombre. Por ejemplo:
En nuestra consulta queremos obtener solo la información relativa a su peso. Hay mucha información contenida en la respuesta, pero solo necesitamos la relativa al peso (“weight”).

En nuestra interfaz tenemos:
- Un elemento TextMeshPro que etiqueta nuestro Input.
- Input (cuadro de texto) donde introducir el nombre del pokemon a encontrar.
- Botón “Buscar peso” que consulta la API
- Elemento TextMeshPro que muestra el peso del Pokemon.


Razones por las que es útil la presente tarea:
- Práctica de la creación de queries utilizando información proporcionada por el usuario.
- Lectura de un único item en lugar de un array desde nuestra API.
