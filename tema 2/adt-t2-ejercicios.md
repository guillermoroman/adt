# ADT - Tema 2 - Ejercicios

[Soluciones: Parte 1](https://github.com/guillermoroman/adt-t2-ejercicios-parte1) - Ejercicios 1 - 22

### Ejercicio 1: Verificar permisos de lectura, escritura y ejecución

**Objetivo:** Usar los métodos `canRead()`, `canWrite()` y `canExecute()` para comprobar los permisos de un archivo o directorio. Tenemos que lograr proporcionar a nuestro programa contextos suficientes para que se den todos los casos posibles para cada uno de los métodos, es decir, proporcionar la ruta tanto a un archivo con derechos de lectura, otro sin derechos, 


### Ejercicio 2: Verificar si existe y es archivo o directorio

**Objetivo:** Usar `exists()`, `isDirectory()` y `isFile()` para comprobar si un archivo o directorio existe y su tipo.


### Ejercicio 3: Obtener el tamaño de un archivo

**Objetivo:** Usar `length()` para obtener el tamaño de un archivo.


### Ejercicio 4: Obtener el directorio padre

**Objetivo:** Usar `getParent()` y `getParentFile()` para obtener el directorio padre de un archivo.


### Ejercicio 5: Listar el contenido de un directorio

**Objetivo:** Usar `list()` y `listFiles()` para listar los archivos en un directorio.


### Ejercicio 6: Crear archivos

**Objetivo:** Usar `createNewFile()` y `createTempFile()` para crear archivos nuevos y temporales.


### Ejercicio 7: Eliminar y renombrar archivos

**Objetivo:** Usar `delete()` y `renameTo()` para eliminar y renombrar archivos.


### Ejercicio 8: Crear un directorio

**Objetivo:** Usar `mkdir()` para crear un nuevo directorio.


### Ejercicio 9: Convertir a objeto Path

**Objetivo:** Usar `toPath()` para convertir un archivo en un objeto `Path`.

### Ejercicio 10: Abrir un archivo y comprobar su acceso
Escribe un programa en Java que abra un archivo de texto (por ejemplo, `archivo.txt`) usando la clase `FileInputStream`. El programa debe intentar abrir el archivo, mostrar un mensaje en la consola que indique si el archivo se ha abierto correctamente o si ha ocurrido un error. Asegúrate de que el archivo se cierre correctamente después de ser abierto, incluso si ocurre un error.

### Ejercicio 11: Leer y mostrar los primeros 5 bytes de un archivo
Escribe un programa en Java que abra un archivo de texto (`archivo.txt`) y lea los primeros 5 bytes de su contenido. Los bytes leídos deben ser almacenados en un array de bytes de tamaño 5, y luego convertidos en una cadena de texto para ser mostrados en la consola. Si el archivo tiene menos de 5 bytes, el programa debe manejar correctamente esa situación.

### Ejercicio 12: Leer una parte específica de un archivo en un buffer más grande
Escribe un programa en Java que abra un archivo de texto (`archivo.txt`) y lea los primeros 5 bytes de su contenido. Los datos leídos deben ser almacenados en un array de bytes de tamaño 10, comenzando desde la posición 0 del array. Luego, convierte los bytes leídos en una cadena de texto y muestra el contenido en la consola.

### Ejercicio 13: Escribir un solo carácter en un archivo

Crea un método que escriba un único carácter en un archivo. Usa un OutputStream y asegúrate de que el archivo se cree en caso de que no exista.

Requisitos:

	•	Escribe el carácter 'A' en un archivo llamado output.txt.
	•	Asegúrate de que el archivo se cierre correctamente, usando try-with-resources.

### Ejercicio 14: Escribir un mensaje desde un array de bytes

Crea un método que escriba un mensaje completo en un archivo utilizando un array de bytes.

Requisitos:

	•	Usa un archivo llamado mensaje.txt.
	•	Convierte el string "Hola, mundo!" a un array de bytes y escribe ese array en el archivo.
	•	Usa OutputStream para abrir el archivo en modo de adición (append).

### Ejercicio 15: Escribir una serie de números en un archivo

Crea un método que escriba los números del 1 al 10 en un archivo, cada uno en una línea nueva.

Requisitos:

	•	El archivo se llamará numeros.txt.
	•	Usa un bucle para escribir los números en el archivo, uno por uno, utilizando OutputStream.
	•	Asegúrate de que los números se escriban en formato ASCII (por ejemplo, '1' en lugar de 1).

### Ejercicio 16: Leer un archivo carácter por carácter

Crea un método que lea un archivo carácter por carácter y los imprima en consola.

Requisitos:

	•	Usa un archivo llamado archivo.txt.
	•	Utiliza un InputStream para leer los datos.
	•	Implementa un bucle que lea hasta llegar al final del archivo (EOF).

### Ejercicio 17: Leer un archivo y almacenar el contenido en un array de bytes

Crea un método que lea el contenido de un archivo y lo almacene en un array de bytes.

Requisitos:

	•	El archivo se llamará datos.txt.
	•	Usa un array de tamaño 1024 bytes.
	•	Muestra en consola cuántos bytes se han leído y el contenido en forma de caracteres.

### Ejercicio 18: Escribir una matriz de caracteres en un archivo

Crea un método que escriba una matriz (array de dos dimensiones) de caracteres en un archivo.

Requisitos:

	•	La matriz tendrá las siguientes filas:

['H', 'o', 'l', 'a'],
['J', 'a', 'v', 'a'],
['M', 'u', 'n', 'd', 'o']


	•	Escribe cada fila en una línea nueva del archivo matriz.txt.
	•	Usa OutputStream para escribir los datos.

### Ejercicio 19: Leer un archivo en fragmentos

Crea un método que lea un archivo en fragmentos usando un array de bytes y lo muestre en consola.

Requisitos:

	•	Usa un archivo llamado fragmentos.txt.
	•	Lee el archivo en bloques de 50 bytes usando un bucle hasta llegar al final del archivo.
	•	Imprime cada fragmento en la consola.

### Ejercicio 20: Crear un archivo de copia

Crea un método que copie el contenido de un archivo a otro.

Requisitos:

	•	Lee el archivo original.txt y copia su contenido a un archivo nuevo llamado copia.txt.
	•	Lee y escribe en fragmentos de 256 bytes.
	•	Asegúrate de manejar excepciones adecuadamente para casos donde el archivo original no exista.

### Ejercicio 21: Contar las palabras en un archivo

Crea un método que cuente cuántas palabras hay en un archivo.

Requisitos:

	•	El archivo se llamará texto.txt y contendrá texto con varias líneas y palabras separadas por espacios y saltos de línea.
	•	Usa InputStream para leer el archivo en fragmentos, y cuenta las palabras usando un método que separe por espacios (" ") y saltos de línea ("\n").
	•	Imprime el total de palabras en la consola.

### Ejercicio 22: Realizar estadísticas de un archivo de texto

Crea un método que analice un archivo de texto y devuelva las siguientes estadísticas: número total de caracteres, número de palabras, y número de líneas.

Requisitos:

	•	Usa un archivo llamado estadisticas.txt.
	•	Utiliza un InputStream para leer el archivo.
	•	Calcula:
	•	Total de caracteres (incluyendo espacios y saltos de línea).
	•	Total de palabras.
	•	Total de líneas.
	•	Muestra estas estadísticas en la consola.

### Ejercicio 23:
Necesitamos un programa capaz de leer registros de una clase `Client`, que deberemos leer desde un archivo ´.csv´.
1. Leer datos de un archivo CSV: El archivo debe contener información de varios clientes en formato CSV, por ejemplo:
```csv
id,nombre,edad,email
1,Juan,25,juan@example.com
2,Ana,30,ana@example.com
3,Carlos,28,carlos@example.com
```

2. Crear objetos Client: A partir de cada línea del CSV, se creará un objeto de tipo `Client`, que almacenará la información del cliente (ID, nombre, edad, email).
3. Guardar en binario: Luego de crear los objetos `Client`, estos se almacenarán en un ArrayList. Finalmente, el programa debe escribir ese `ArrayList` en un archivo binario usando `ObjectOutputStream`.
4. Comprobar que los archivos se han guardado correctamente.

### Ejercicio 24:
Crear un programa que lea del archivo csv [censo_perros_salvajes.csv](https://github.com/guillermoroman/adt/blob/main/resources/censo-perros-peligrosos.csv) , obtenido de la web de [Datos abiertos de Gijón](https://www.gijon.es/en/datos), para crear un ArrayList de elementos de la clase `RazaPeligrosa` que deberás crear.
Crea un método que imprima por pantalla dicha lista, utilizando el método `toString` de la clase `RazaPeligrosa`

**Parte 2:**
Crea un elemento `HashMap` donde se almacenarán las distintas razas peligrosas, así como los totales de cada una de las mismas. Deberá imprimir dicha lista por pantalla.


### Ejercicio 25: XML & JSON
Crea un programa capaz de leer desde un archivo en formato XML, una serie de objetos del tipo película. El programa deberá crear un ArrayList, que deberá después almacenar en un archivo JSON.
```XML
<peliculas>
    <pelicula>
        <nombre>El Padrino</nombre>
        <director>Francis Ford Coppola</director>
        <año>1972</año>
        <duracion>175</duracion>
    </pelicula>
    <pelicula>
        <nombre>Interstellar</nombre>
        <director>Christopher Nolan</director>
        <año>2014</año>
        <duracion>169</duracion>
    </pelicula>
    <pelicula>
        <nombre>La La Land</nombre>
        <director>Damien Chazelle</director>
        <año>2016</año>
        <duracion>128</duracion>
    </pelicula>
</peliculas>
```
### Ejercicio 26

Crea un breve programa que:
- lea con jackson de un fichero json.
- cree un array de objetos de tipo mascota com campo nobre, especie y edad
- imprima un informe en un archivo txt.
- sea capaz de incluir tambien mascotas desde un csv que se leera a mano, no con un parseador.

`mascotas.json`
```json
[
    {"nombre": "Firulais", "especie": "Perro", "edad": 5},
    {"nombre": "Michi", "especie": "Gato", "edad": 3}
]
```

`mascotas.csv`
```csv
Luna,Perro,4
Felix,Gato,2
```

