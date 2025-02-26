# Unidad Didáctica: Acceso a APIs REST con Java

## 1. Objetivos

1. Comprender cómo realizar peticiones HTTP (GET, POST, …) en Java utilizando la clase `HttpClient`.
2. Manejar el intercambio de información en formato JSON y mapearlo a objetos Java con Jackson.
3. Implementar métodos para **obtener** todos los objetos, **obtener** uno por ID y **crear** nuevos objetos a través de la API.
4. Aplicar buenas prácticas de manejo de errores y estructuración de código.

## 2. Estructura de Proyecto y Dependencias

Se recomienda crear un proyecto **Maven** para gestionar las dependencias.  
En el `pom.xml` (Maven), incluir algo como:

```xml
<dependencies>
    <!-- Jackson para convertir JSON <-> objetos Java -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.14.2</version>
    </dependency>
</dependencies>
```

## 3. Modelo de Datos: Clase `Task`

Creamos un paquete `org.example.model` (o el que prefieras) con la clase `Task`. Esta clase debe ser serializable con Jackson, por lo que conviene usar el anotador `@JsonIgnoreProperties(ignoreUnknown = true)` para ignorar atributos que el servidor pudiera devolver y no tengamos definidos.

```java
package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    // Constructores
    public Task() {
    }

    public Task(Long id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // toString para facilitar la impresión
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
```

**Campos**:
- `id`: identificador único de la tarea (puede generarlo la base de datos).
- `title`: título descriptivo de la tarea.
- `description`: detalle o contenido de la tarea.
- `completed`: si la tarea está completada o no.


## 4. Clase Principal: Ejemplo de Llamadas HTTP

Creamos una clase `MainApp` (u otro nombre que prefieras) donde centralizar la lógica de peticiones. La idea es:
1. **Obtener** todas las tareas (`getAllTasks`).
2. **Obtener** una tarea por ID (`getTaskById`).
3. **Crear** una nueva tarea (`postTask`).

### Código de Ejemplo

```java
package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MainApp {
    
    // URL base de la API (ajustar según tu servidor o entorno)
    private static final String BASE_URL = "http://localhost:8080/api";
    
    // Cliente HTTP reutilizable
    private static final HttpClient client = HttpClient.newHttpClient();

    // Mapper de Jackson para convertir JSON <-> Objetos Java
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        
        // 1. Obtener todas las tareas
        ArrayList<Task> allTasks = getAllTasks();
        System.out.println("=== LISTA DE TAREAS ===");
        allTasks.forEach(System.out::println);

        // 2. Obtener una tarea por ID (ejemplo ID=1)
        System.out.println("\n=== TAREA CON ID 1 ===");
        Task taskById = getTaskById(1L);
        if (taskById != null) {
            System.out.println(taskById);
        } else {
            System.out.println("No se encontró la tarea con ID=1");
        }

        // 3. Crear una nueva tarea (POST)
        Task newTask = new Task(null, "Estudiar Java HTTP", "Repasar peticiones HTTP con HttpClient", false);
        Task createdTask = postTask(newTask);
        if (createdTask != null) {
            System.out.println("\nTarea creada correctamente: " + createdTask);
        } else {
            System.out.println("\nOcurrió un error al crear la tarea.");
        }
    }

    // Método para obtener todas las tareas (GET /tasks)
    public static ArrayList<Task> getAllTasks() {
        String url = BASE_URL + "/tasks";
        ArrayList<Task> result = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        try {
            // Enviamos la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                // Convertimos el JSON en una lista de Task
                result = mapper.readValue(
                        response.body(),
                        new TypeReference<ArrayList<Task>>() {}
                );
            } else {
                System.out.println("Error al obtener tareas: HTTP " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Método para obtener una tarea por ID (GET /tasks/{id})
    public static Task getTaskById(Long id) {
        String url = BASE_URL + "/tasks/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        try {
            // Enviamos la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                // Convertimos el JSON en un objeto Task
                return mapper.readValue(response.body(), Task.class);
            } else {
                System.out.println("Error al obtener tarea con ID=" + id + ": HTTP " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null; // Devolvemos null si no se pudo recuperar la tarea
    }

    // Método para crear una nueva tarea (POST /tasks)
    public static Task postTask(Task task) {
        String url = BASE_URL + "/tasks";
        
        try {
            // Convertimos el objeto Task a JSON
            String requestBody = mapper.writeValueAsString(task);

            // Construimos la solicitud POST con el cuerpo JSON
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviamos la solicitud y obtenemos la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Verificamos si la creación fue exitosa (p.ej. HTTP 201)
            if (response.statusCode() == 201 || response.statusCode() == 200) {
                // Convertimos la respuesta a un objeto Task
                return mapper.readValue(response.body(), Task.class);
            } else {
                System.out.println("Error al crear tarea: HTTP " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null; // En caso de error, devolvemos null
    }
}
```

### Explicación Resumida

1. **`HttpClient`**  
   - Creamos un único objeto `HttpClient` para reutilizar en las distintas llamadas.
2. **Peticiones GET**  
   - Se construye un `HttpRequest` con `.GET()`, especificando la URL.  
   - Al enviar (`client.send(...)`), obtenemos un `HttpResponse<String>` que contiene el cuerpo en formato JSON.  
   - Usamos Jackson (`ObjectMapper`) para convertir esa cadena JSON en un objeto Java, ya sea `Task` individual o `List<Task>` usando un `TypeReference`.
3. **Petición POST**  
   - Convertimos el objeto `Task` a JSON (`mapper.writeValueAsString`).  
   - Creamos una solicitud `.POST(...)` con `BodyPublishers.ofString(...)` para enviar el cuerpo como texto.  
   - Indicamos `Content-Type: application/json` en la cabecera.  
   - Al recibir la respuesta, si es exitosa (p.ej. 201 o 200), parseamos nuevamente el JSON que devuelva el servidor para obtener la tarea creada.


## 5. Posibles Extensiones

1. **Actualizar (PUT)**: De forma similar, se puede implementar un método `putTask(Task task, Long id)` que envíe la información para actualizar una tarea existente.
2. **Eliminar (DELETE)**: Método `deleteTask(Long id)` que construya una solicitud `.DELETE()` y reciba un código de estado 204 o similar.
3. **Manejo de Errores**: Hacer validaciones más completas (por ejemplo, retrys, manejo de excepciones específicas, logs con librerías como Log4j o SLF4J).
4. **Configuración Avanzada**: Usar cabeceras de autenticación, tokens, o certificados si la API lo requiere.

Con ello, se obtiene un primer acercamiento a la integración de aplicaciones Java con APIs externas o internas, un paso esencial en el desarrollo de software moderno y distribuido.
