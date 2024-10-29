### Ejercicio 1
Investiga los diferentes tipos de **formatos de texto estructurado** o **formatos de intercambio de datos**. Responde a las siguientes preguntas:
- ¿Qué son los formatos de texto estructurado?
- ¿Para qué se usan en general?
- ¿Cuáles son los formatos más extendidos?
- Qué ventajas, desventajas y usos tiene cada uno?
- ¿Cómo se almacenaría la siguiente información en cada uno de ellos?
  
### Ejemplo de información jerarquizada:
```plaintext
Biblioteca: "Biblioteca Central"
Ubicación: "Calle Principal, 123"
Libros:
  - Título: "El Principito"
    Autor: Antoine de Saint-Exupéry
    Año: 1943
    Género: "Ficción"
  - Título: "1984"
    Autor: George Orwell
    Año: 1949
    Género: "Distopía"
  - Título: "Cien años de soledad"
    Autor: Gabriel García Márquez
    Año: 1967
    Género: "Realismo mágico"
```

Ahora veamos cómo se almacenaría esta información en varios formatos:

---

### 1. **JSON** (JavaScript Object Notation)
JSON usa una estructura de pares clave-valor y listas para representar los datos de manera jerárquica.

```json
{
  "biblioteca": {
    "nombre": "Biblioteca Central",
    "ubicacion": "Calle Principal, 123",
    "libros": [
      {
        "titulo": "El Principito",
        "autor": {
          "nombre": "Antoine",
          "apellido": "de Saint-Exupéry"
        },
        "año": 1943,
        "genero": "Ficción"
      },
      {
        "titulo": "1984",
        "autor": {
          "nombre": "George",
          "apellido": "Orwell"
        },
        "año": 1949,
        "genero": "Distopía"
      },
      {
        "titulo": "Cien años de soledad",
        "autor": {
          "nombre": "Gabriel",
          "apellido": "García Márquez"
        },
        "año": 1967,
        "genero": "Realismo mágico"
      }
    ]
  }
}
```

**Ventajas de JSON**:
- Legible por humanos y fácil de entender.
- Muy utilizado en aplicaciones web.
- Flexible para representar datos complejos.

**Desventajas**:
- No soporta comentarios.
- El tamaño del archivo puede crecer con datos más grandes.

---

### 2. **YAML** (YAML Ain't Markup Language)
YAML es similar a JSON, pero es más legible para humanos debido a su simplicidad y uso de indentación.

```yaml
biblioteca:
  nombre: "Biblioteca Central"
  ubicacion: "Calle Principal, 123"
  libros:
    - titulo: "El Principito"
      autor:
        nombre: "Antoine"
        apellido: "de Saint-Exupéry"
      año: 1943
      genero: "Ficción"
    - titulo: "1984"
      autor:
        nombre: "George"
        apellido: "Orwell"
      año: 1949
      genero: "Distopía"
    - titulo: "Cien años de soledad"
      autor:
        nombre: "Gabriel"
        apellido: "García Márquez"
      año: 1967
      genero: "Realismo mágico"
```

**Ventajas de YAML**:
- Muy legible para humanos.
- Soporta comentarios.
- Ideal para configuraciones sencillas y datos estructurados.

**Desventajas**:
- Sensible a errores de indentación.
- Menos eficiente que JSON para grandes volúmenes de datos.

---

### 3. **XML** (Extensible Markup Language)
XML organiza los datos jerárquicos usando etiquetas anidadas, similar a HTML.

```xml
<biblioteca>
  <nombre>Biblioteca Central</nombre>
  <ubicacion>Calle Principal, 123</ubicacion>
  <libros>
    <libro>
      <titulo>El Principito</titulo>
      <autor>
        <nombre>Antoine</nombre>
        <apellido>de Saint-Exupéry</apellido>
      </autor>
      <año>1943</año>
      <genero>Ficción</genero>
    </libro>
    <libro>
      <titulo>1984</titulo>
      <autor>
        <nombre>George</nombre>
        <apellido>Orwell</apellido>
      </autor>
      <año>1949</año>
      <genero>Distopía</genero>
    </libro>
    <libro>
      <titulo>Cien años de soledad</titulo>
      <autor>
        <nombre>Gabriel</nombre>
        <apellido>García Márquez</apellido>
      </autor>
      <año>1967</año>
      <genero>Realismo mágico</genero>
    </libro>
  </libros>
</biblioteca>
```

**Ventajas de XML**:
- Muy estructurado y flexible.
- Soporta esquemas (XSD) para validar la estructura de los datos.
- Permite el uso de nombrespacios para evitar conflictos de nombres.

**Desventajas**:
- Verborreico y puede ser innecesariamente largo.
- Menos eficiente en términos de espacio y procesamiento en comparación con JSON o YAML.

---

### 4. **CSV (Comma-Separated Values)**
En CSV, los datos se almacenan en un formato tabular (plano), por lo que las estructuras jerárquicas no son directamente representables. La información tendría que desnormalizarse (es decir, repetirse).

```csv
biblioteca,nombre,ubicacion
,Biblioteca Central,Calle Principal, 123

libro,titulo,autor_nombre,autor_apellido,año,genero
1,El Principito,Antoine,de Saint-Exupéry,1943,Ficción
2,1984,George,Orwell,1949,Distopía
3,Cien años de soledad,Gabriel,García Márquez,1967,Realismo mágico
```

**Ventajas de CSV**:
- Muy simple y fácil de leer en aplicaciones como hojas de cálculo.
- Ideal para datos tabulares y para exportar e importar grandes volúmenes de datos.

**Desventajas**:
- No soporta datos jerárquicos de manera nativa.
- Problemas con delimitadores en los datos (ej. comas dentro de campos).

---

### 5. **TOML (Tom's Obvious, Minimal Language)**
TOML es un formato sencillo y legible, principalmente utilizado en archivos de configuración.

```toml
[biblioteca]
nombre = "Biblioteca Central"
ubicacion = "Calle Principal, 123"

[[biblioteca.libros]]
titulo = "El Principito"
autor_nombre = "Antoine"
autor_apellido = "de Saint-Exupéry"
año = 1943
genero = "Ficción"

[[biblioteca.libros]]
titulo = "1984"
autor_nombre = "George"
autor_apellido = "Orwell"
año = 1949
genero = "Distopía"

[[biblioteca.libros]]
titulo = "Cien años de soledad"
autor_nombre = "Gabriel"
autor_apellido = "García Márquez"
año = 1967
genero = "Realismo mágico"
```

**Ventajas de TOML**:
- Fácil de leer y escribir para humanos.
- Menos propenso a errores que YAML debido a su sintaxis simple.

**Desventajas**:
- Menos conocido que JSON o YAML.
- Menos soporte para estructuras muy complejas.

---

### 6. **Protobuf (Protocol Buffers)**
Protobuf es un formato binario que requiere un esquema. A continuación, el esquema `.proto` y los datos serían serializados.

```proto
message Biblioteca {
  string nombre = 1;
  string ubicacion = 2;
  repeated Libro libros = 3;
}

message Libro {
  string titulo = 1;
  Autor autor = 2;
  int32 año = 3;
  string genero = 4;
}

message Autor {
  string nombre = 1;
  string apellido = 2;
}
```

El archivo binario resultante no es legible por humanos, pero sería muy eficiente en términos de espacio.

**Ventajas de Protobuf**:
- Alta eficiencia en espacio y procesamiento.
- Tipado fuerte y versionado de los datos.

**Desventajas**:
- No legible por humanos.
- Requiere esquemas predefinidos.

---

### Conclusión:
- Si necesitas **legibilidad** y flexibilidad para jerarquías de datos complejas, **JSON** o **YAML** serían las mejores opciones.
- Si trabajas con **configuraciones simples**, **TOML** o **INI** pueden ser útiles.
- Para **grandes volúmenes de datos** o **comunicaciones rápidas**, **Protobuf** o **Avro** son muy eficientes.
- **CSV** es excelente para datos **tabulares**, pero no para estructuras jerárquicas complejas.