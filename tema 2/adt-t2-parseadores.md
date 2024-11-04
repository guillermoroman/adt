## JSON

Para parsear un documento JSON en Java, se suele utilizar bibliotecas como **Jackson** o **Gson**, que facilitan la conversión entre objetos Java y JSON. Ambas bibliotecas son muy eficientes y fáciles de usar, y puedes elegir la que prefieras según tus necesidades o preferencias. Jackson es más potente y tiene más opciones de configuración, mientras que Gson es ligera y muy sencilla de usar. En nuestro caso utilizaremos la biblioteca Jackson.

### Librería Jackson
Si utilizamos Maven, debemos añadir una dependencia a nuestro archivo `pom.xml`:
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.18.0</version>
</dependency>
```

La versión anterior es orientativa y corresponde a la utilizada en el momento de la redacción de este texto. Se recomienda la versión más actualizada que se pueda encontrar en el repositorio de Maven.
### Leer JSON desde archivo (1 elemento)
Veamos un ejemplo simple de cómo leer un archivo JSON que contiene la representación de un único objeto y convertirlo en un objeto Java de una clase diseñada por nosotros:
```java
// Definir la clase que representa el JSON
class Persona {
    private String nombre;
    private int edad;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", edad=" + edad + "]";
    }
}
```

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class EjemploJackson {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parsear el JSON desde un archivo
            Persona persona = objectMapper.readValue(new File("persona.json"), Persona.class);
            // Imprimir los datos
            System.out.println(persona);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Es tan sencillo como instanciar un objeto `ObjectMapper`, al cual pediremos a través del método `readValue`que lea el contenido de un archivo representado por un objeto de tipo `File`.

Ejemplo de archivo persona.json:
```json
{
    "nombre": "Juan",
    "edad": 30
}
```

### Leer JSON con desde archivo (array de elementos)

Crear un archivo JSON con varias personas
```json
[
    {
        "nombre": "Juan",
        "edad": 30
    },
    {
        "nombre": "Ana",
        "edad": 25
    },
    {
        "nombre": "Pedro",
        "edad": 40
    }
]
```

Código en Java con Jackson para parsear una lista de personas:
```java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EjemploJacksonList {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parsear el JSON a una lista de objetos Persona
            List<Persona> personas = objectMapper.readValue(new File("personas.json"), new TypeReference<List<Persona>>() {});
            // Imprimir la lista de personas
            for (Persona persona : personas) {
                System.out.println(persona);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
  
1. **Archivo JSON**: El archivo personas.json ahora contiene un array de objetos JSON, cada uno representando una persona.
2. TypeReference: Jackson no puede inferir automáticamente el tipo genérico de una lista, por lo que usamos `new TypeReference<List<Persona>>() {}` para indicarle que debe mapear el JSON a una lista de objetos Persona.
3. **Lectura y Mapeo**: Usamos readValue para leer el JSON desde el archivo y convertirlo en una lista de objetos Persona.
4. **Impresión de la lista**: Iteramos sobre la lista de personas y las imprimimos usando un bucle for.

Resultado de la ejecución:
```
Persona [nombre=Juan, edad=30]
Persona [nombre=Ana, edad=25]
Persona [nombre=Pedro, edad=40]
```
  
### Escribir un objeto en JSON
```java
public class EscribirJSON {
    public static void main(String[] args) {

        // Crear un solo objeto Persona
        Persona persona = new Persona("Juan", 30);
        
        // Crear el ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Escribir el objeto Persona en un archivo JSON
            objectMapper.writeValue(new File("salida_persona.json"), persona);
            System.out.println("Archivo JSON escrito correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
  
Explicación:
1. **Objeto Java**: En lugar de una lista de objetos, hemos creado un solo objeto Persona con los datos que queremos escribir en el archivo JSON.
2. **writeValue()**: El método `writeValue(File file, Object value)` convierte el objeto Persona en JSON y lo escribe en el archivo especificado. Si el archivo no existe, Jackson lo crea; si ya existe, lo sobreescribe.
3. **Resultado**: El archivo salida_persona.json contendrá el JSON con los datos del objeto Persona.

Ejemplo del archivo JSON generado (salida_persona.json):
```json
{
    "nombre": "Juan",
    "edad": 30
}
```
  
**Formato “Pretty Print”**
Si deseas que el archivo JSON esté formateado con saltos de línea e indentaciones, puedes utilizar el método writerWithDefaultPrettyPrinter(), como en el caso del array de objetos. Aquí tienes el ejemplo:
```java
objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("salida_persona_pretty.json"), persona);
```

Esto generará un archivo salida_persona_pretty.json con un formato más legible:
```json
{
    "nombre": "Juan",
    "edad": 30
}
```

### Escribir array de objetos en JSON

Para escribir un archivo JSON a partir de un array de objetos en Java utilizando Jackson, utilizamos el método `writeValue()` de la clase `ObjectMapper`.

Ejemplo:
```java
public class EscribirJSON {
    public static void main(String[] args) {
        // Crear la lista de personas
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Juan", 30));
        personas.add(new Persona("Ana", 25));
        personas.add(new Persona("Pedro", 40));

        // Crear el ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Escribir la lista de personas en un archivo JSON
            objectMapper.writeValue(new File("salida_personas.json"), personas);
            System.out.println("Archivo JSON escrito correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

`writeValue()`: El método `writeValue(File file, Object value)` convierte el objeto value (en este caso, la lista de personas) en JSON y lo escribe en el archivo que pasamos como primer argumento. Si el archivo no existe, Jackson lo creará; si ya existe, lo sobrescribirá.

JSON generado:
```json
[
    {
        "nombre": "Juan",
        "edad": 30
    },
    {
        "nombre": "Ana",
        "edad": 25
    },

    {
        "nombre": "Pedro",
        "edad": 40
    }
]
```
  
**Nota:**
Jackson formatea el JSON con la estructura adecuada automáticamente, pero podemos especificar si queremos un formato bonito o minificado. Si quisiéramos formatear el JSON con indentaciones (un “pretty print”), puedes usar el método `writerWithDefaultPrettyPrinter()` de `ObjectMapper`, de la siguiente forma:
```java
objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("salida_personas_pretty.json"), personas);
```

Este código generará un archivo JSON con una mejor presentación, con saltos de línea e indentación.

### Anotaciones JSON

Las anotaciones en **Jackson** se usan para controlar cómo se serializan y deserializan los objetos Java a JSON y viceversa. A veces, los nombres de los campos de los objetos Java no coinciden con los campos del JSON, o queremos controlar cómo se manejan algunos aspectos del proceso (como omitir campos, cambiar nombres de propiedades, ignorar valores nulos, etc.). Las anotaciones de Jackson permiten personalizar este comportamiento de manera flexible.

**1. @JsonProperty**
Esta anotación se usa para mapear un campo Java a una propiedad JSON con un nombre diferente. Si el nombre del campo en la clase Java no coincide con el nombre del campo en el JSON, puedes usar esta anotación para establecer la correspondencia correcta.

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonProperty;

class Persona {
    @JsonProperty("nombreCompleto")
    private String nombre;

    @JsonProperty("edadActual")
    private int edad;
    
    // Constructor, Getters y Setters
}
```
  
En este ejemplo, Jackson mapeará el campo nombre en Java al campo nombreCompleto en el JSON, y edad en Java al campo edadActual en el JSON.

```json
{
    "nombreCompleto": "Juan",
    "edadActual": 30
}
```

**2. @JsonIgnore**
Esta anotación se utiliza para ignorar un campo en el proceso de serialización o deserialización. Es útil cuando no quieres que ciertos campos se incluyan en el JSON.

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonIgnore;

class Persona {
    private String nombre;
    
    @JsonIgnore
    private int edad;  // Este campo será ignorado
    
    // Constructor, Getters y Setters
}
```
  
En este caso, el campo edad no se incluirá ni en el JSON generado ni se leerá cuando deserialices el JSON.

```json
{
    "nombre": "Juan"
}
```

  
**3. @JsonInclude**
Controla cuándo se debe incluir una propiedad en el JSON. Por ejemplo, puedes especificar que se omitan los valores nulos o los valores por defecto.

Ejemplo:

```java
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

class Persona {
    private String nombre;

    @JsonInclude(Include.NON_NULL)  // No incluir si es null
    private Integer edad;
    
    // Constructor, Getters y Setters
}
```
  

En este ejemplo, el campo edad solo se incluirá en el JSON si no es null.
```json
{
    "nombre": "Juan",
    "edad": 30
}
```
  
Ejemplo de JSON cuando edad es null:
```json
{
    "nombre": "Juan"
}
```


**4. @JsonIgnoreProperties**
Se usa para ignorar múltiples propiedades durante la deserialización. Es útil si deseas ignorar campos que no son relevantes para tu aplicación.

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"apellido", "edad"})

class Persona {
    private String nombre;
    // El campo "apellido" y "edad" serán ignorados

    // Constructor, Getters y Setters
}
```

Si el JSON contiene campos apellido o edad, estos serán ignorados y no causarán problemas durante la deserialización.

Ejemplo de JSON (el cual contiene propiedades que serán ignoradas):
```json
{
    "nombre": "Juan",
    "apellido": "Pérez",
    "edad": 30
}
```


**5. @JsonCreator y @JsonProperty**
Estas anotaciones se utilizan juntas cuando quieres deserializar un JSON en un objeto Java pero tu clase no tiene un constructor sin argumentos (o deseas un control más detallado sobre la creación del objeto).

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Persona {
    private String nombre;
    private int edad;

    @JsonCreator
    public Persona(@JsonProperty("nombre") String nombre, @JsonProperty("edad") int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    // Getters y Setters
}
```
  
Esto le indica a Jackson cómo debe mapear los campos del JSON a los parámetros del constructor cuando deserializa el objeto.

Ejemplo de JSON:
```json
{
    "nombre": "Juan",
    "edad": 30
}
```

**6. @JsonFormat**
Esta anotación se usa para definir cómo Jackson debería serializar/deserializar los tipos de datos como fechas.

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

class Persona {
    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

    private Date fechaNacimiento;

  

    // Constructor, Getters y Setters

}
```
  
En este ejemplo, Jackson deserializará/serializará el campo fechaNacimiento en el formato dd-MM-yyyy (por ejemplo, “20-12-1990”).

Ejemplo de JSON:
```json
{
    "nombre": "Juan",
    "fechaNacimiento": "20-12-1990"
}
```


**7. @JsonGetter y @JsonSetter**
Se pueden usar para personalizar qué método específico se debe utilizar para serializar o deserializar una propiedad.

Ejemplo:
```java
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

class Persona {
    private String nombre;

    @JsonGetter("nombreCompleto")
    public String getNombre() {
        return nombre;
    }

    @JsonSetter("nombreCompleto")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```
  
En este ejemplo, Jackson utilizará getNombre() para serializar el campo nombre como nombreCompleto, y utilizará setNombre() para deserializar el campo nombreCompleto del JSON.

Ejemplo de JSON:

```json
{
    "nombreCompleto": "Juan"
}
```
  

## XML
Para parsear un archivo XML, utilizaremos las bibliotecas estándar que proporciona la API de Java. La biblioteca `javax.xml.parsers.DocumentBuilder` nos permite leer y manipular un archivo XML en forma de un árbol de nodos (DOM, Document Object Model).

### Parseo de un archivo XML con DocumentBuilder
1. **Agregar las importaciones necesarias:**
```java
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
```

2. **Código para parsear el archivo XML:**
```java
import java.io.File;

public class XMLParserExample {
    public static void main(String[] args) {
        try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crear un DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Parsear el archivo XML
            File file = new File("archivo.xml");
            Document document = builder.parse(file);

            // Normalizar el XML (opcional, elimina nodos vacíos y organiza la estructura)
            document.getDocumentElement().normalize();

            // Obtener el elemento raíz
            Element root = document.getDocumentElement();
            System.out.println("Elemento raíz: " + root.getNodeName());

            // Obtener una lista de nodos por etiqueta
            NodeList nodeList = document.getElementsByTagName("nombre_etiqueta");

            // Recorrer los nodos
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                // Asegurarse de que es un nodo de tipo Elemento
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Obtener el valor de los elementos hijos o atributos
                    String valor = element.getElementsByTagName("subelemento").item(0).getTextContent();

                    System.out.println("Valor del subelemento: " + valor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

• **DocumentBuilderFactory y DocumentBuilder**: Se utilizan para crear un objeto Document, que representa todo el documento XML.
• **normalize()**: Este método ayuda a estructurar el documento eliminando nodos vacíos o redundantes.
• **NodeList**: Una lista de nodos XML. Puedes buscar nodos usando etiquetas específicas, y luego iterar sobre ellos.
• **Element y Node**: Son las clases que te permiten acceder a los elementos y sus valores dentro del archivo XML.

Ejemplo de archivo XML:
```xml
<personas>
    <persona>
        <nombre>Juan</nombre>
        <edad>30</edad>
    </persona>
    <persona>
        <nombre>Ana</nombre>
        <edad>25</edad>
    </persona>
</personas>
```


### Escritura de un documento XML

```java
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WriteXMLPrettyExample {

    public static void main(String[] args) {
        try {
            // Crear una lista de objetos Persona
            List<Persona> personas = new ArrayList<>();
            personas.add(new Persona("Juan", 30));
            personas.add(new Persona("Ana", 25));
            personas.add(new Persona("Carlos", 40));

            // Crear el documento XML
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz <personas>
            Element root = document.createElement("personas");
            document.appendChild(root);

            // Añadir cada persona al XML
            for (Persona persona : personas) {
                // Crear un nuevo elemento <persona>
                Element personaElement = document.createElement("persona");
                root.appendChild(personaElement);

                // Añadir el elemento <nombre>
                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(persona.getNombre()));
                personaElement.appendChild(nombre);

                // Añadir el elemento <edad>
                Element edad = document.createElement("edad");
                edad.appendChild(document.createTextNode(String.valueOf(persona.getEdad())));
                personaElement.appendChild(edad);
            }

            // Crear el transformer para escribir el contenido a un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Prettify el XML: Establecer las propiedades de indentación
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  // 4 espacios de indentación

            // Escribir el contenido al archivo
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("personas_output_pretty.xml"));
            transformer.transform(domSource, streamResult);

            System.out.println("Archivo XML generado exitosamente y con formato legible!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

1. **Creación de Document**: El archivo XML comienza a crearse en memoria.
2. **Creación de elementos** persona **y sus subelementos**: Para cada persona en la lista, se crean nodos persona, nombre y edad.
3. **Escritura en archivo**: El objeto Transformer escribe el contenido del Document en un archivo XML.

### Ejemplo