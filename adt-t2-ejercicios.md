# ADT - Tema 2 - Ejercicios

Para la realización de los ejercicios se puede tomar como referencia el código de ejemplo visto en los apuntes y que se muestra a continuación:

```java
import java.io.File;

public class DirectoryList {
    public static void main(String[] args) {
        // . apunta al directorio actual.
        String path = ".";

        if (args.length > 0) {
           path = args[0];
        }

        File file = new File(path);

        // Comprobar que la ruta es válida
        if (!file.exists()){
            System.out.println("File does not exist");
        }
        else {
            if (file.isFile()){
                System.out.println(file + " is a file");
            }
            else if (file.isDirectory()){
                System.out.println(file + " is a directory. Contents:");
                File[] files = file.listFiles();
                assert files != null;
                for (File f : files){
                    String description = f.isDirectory() ? "/" :
                            f.isFile() ? "_" : "?" ;
                    System.out.println("("+description+")"+f.getName());
                }
            }
        }
    }
}
```

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
Escribe un programa en Java que abra un archivo de texto (`archivo.txt`) y lea los primeros 5 bytes de su contenido. Los datos leídos deben ser almacenados en un array de bytes de tamaño 10, comenzando desde la posición 0 del array. Luego, convierte los bytes leídos en una cadena de texto y muestra el contenido en la consola. Asegúrate de utilizar la instrucción `fich.read(buff, 0, 5)` para realizar la lectura.

---

Estos ejercicios guían progresivamente desde abrir un archivo, hasta leer bytes y trabajar con partes específicas de buffers.
