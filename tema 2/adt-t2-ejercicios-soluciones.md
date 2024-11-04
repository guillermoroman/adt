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

```java
import java.io.File;

public class FilePermissions {
    public static void main(String[] args) {
        File file = new File("test.txt");

        if (file.exists()) {
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
            System.out.println("Can execute: " + file.canExecute());
        } else {
            System.out.println("File does not exist");
        }
    }
}
```

### Ejercicio 2: Verificar si existe y es archivo o directorio

**Objetivo:** Usar `exists()`, `isDirectory()` y `isFile()` para comprobar si un archivo o directorio existe y su tipo.

```java
import java.io.File;

public class FileCheck {
    public static void main(String[] args) {
        File file = new File("test.txt");

        if (file.exists()) {
            if (file.isFile()) {
                System.out.println(file.getName() + " is a file.");
            } else if (file.isDirectory()) {
                System.out.println(file.getName() + " is a directory.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}
```

### Ejercicio 3: Obtener el tamaño de un archivo

**Objetivo:** Usar `length()` para obtener el tamaño de un archivo.

```java
import java.io.File;

public class FileSize {
    public static void main(String[] args) {
        File file = new File("test.txt");

        if (file.exists() && file.isFile()) {
            System.out.println("File size: " + file.length() + " bytes");
        } else {
            System.out.println("File does not exist or is not a file.");
        }
    }
}
```

### Ejercicio 4: Obtener el directorio padre

**Objetivo:** Usar `getParent()` y `getParentFile()` para obtener el directorio padre de un archivo.

```java
import java.io.File;

public class FileParent {
    public static void main(String[] args) {
        File file = new File("test.txt");

        if (file.exists()) {
            System.out.println("Parent directory: " + file.getParent());
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                System.out.println("Parent directory file object: " + parentFile.getName());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}
```

### Ejercicio 5: Listar el contenido de un directorio

**Objetivo:** Usar `list()` y `listFiles()` para listar los archivos en un directorio.

```java
import java.io.File;

public class DirectoryContents {
    public static void main(String[] args) {
        File directory = new File(".");

        if (directory.exists() && directory.isDirectory()) {
            String[] fileList = directory.list();
            File[] fileObjects = directory.listFiles();

            System.out.println("Files (list):");
            for (String fileName : fileList) {
                System.out.println(fileName);
            }

            System.out.println("\nFiles (listFiles):");
            for (File file : fileObjects) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
```

### Ejercicio 6: Crear archivos

**Objetivo:** Usar `createNewFile()` y `createTempFile()` para crear archivos nuevos y temporales.

```java
import java.io.File;
import java.io.IOException;

public class FileCreation {
    public static void main(String[] args) throws IOException {
        File file = new File("newFile.txt");

        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }

        File tempFile = File.createTempFile("tempFile", ".txt");
        System.out.println("Temporary file created: " + tempFile.getAbsolutePath());
    }
}
```

### Ejercicio 7: Eliminar y renombrar archivos

**Objetivo:** Usar `delete()` y `renameTo()` para eliminar y renombrar archivos.

```java
import java.io.File;

public class FileDeleteRename {
    public static void main(String[] args) {
        File file = new File("newFile.txt");

        // Renombrar archivo
        File newFile = new File("renamedFile.txt");
        if (file.renameTo(newFile)) {
            System.out.println("File renamed to: " + newFile.getName());
        } else {
            System.out.println("Failed to rename file.");
        }

        // Eliminar archivo
        if (newFile.delete()) {
            System.out.println("File deleted: " + newFile.getName());
        } else {
            System.out.println("Failed to delete file.");
        }
    }
}
```

### Ejercicio 8: Crear un directorio

**Objetivo:** Usar `mkdir()` para crear un nuevo directorio.

```java
import java.io.File;

public class DirectoryCreation {
    public static void main(String[] args) {
        File directory = new File("newDirectory");

        if (directory.mkdir()) {
            System.out.println("Directory created: " + directory.getName());
        } else {
            System.out.println("Failed to create directory.");
        }
    }
}
```

### Ejercicio 9: Convertir a objeto Path

**Objetivo:** Usar `toPath()` para convertir un archivo en un objeto `Path`.

```java
import java.io.File;
import java.nio.file.Path;

public class FileToPath {
    public static void main(String[] args) {
        File file = new File("test.txt");
        
        if (file.exists()) {
            Path filePath = file.toPath();
            System.out.println("Path: " + filePath);
        } else {
            System.out.println("File does not exist.");
        }
    }
}
```
