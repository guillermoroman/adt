# **Paginación en API REST con Spring Boot**
## **1. Introducción a la paginación**
Cuando una API devuelve grandes volúmenes de datos, es conveniente dividir la respuesta en partes más pequeñas mediante paginación. Spring Boot ofrece una implementación sencilla a través de **Spring Data JPA** con las interfaces `Pageable` y `Page`.

---

## **2. Configuración básica en el Repositorio**
Spring Data JPA proporciona la interfaz `PagingAndSortingRepository`, pero normalmente se usa `JpaRepository`, que ya la extiende.

Ejemplo de un **repositorio** con paginación:
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findAll(Pageable pageable);
}
```
Aquí, el método `findAll(Pageable pageable)` permite obtener los datos de manera paginada.

---

## **3. Uso de `Pageable` en el Servicio**
El servicio debe recibir el objeto `Pageable` y delegar la consulta al repositorio:

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Page<Producto> obtenerProductosPaginados(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }
}
```

---

## **4. Implementación en el Controlador**
Para recibir los parámetros de paginación en el controlador, se usa `@RequestParam` en combinación con `Pageable`.

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Page<Producto>> obtenerProductos(Pageable pageable) {
        Page<Producto> productos = productoService.obtenerProductosPaginados(pageable);
        return ResponseEntity.ok(productos);
    }
}
```

---

## **5. Uso de parámetros en la URL**
Spring Boot permite especificar los parámetros de paginación directamente en la URL de la petición:

- `page`: Número de la página (comienza en 0)
- `size`: Cantidad de elementos por página
- `sort`: Campo por el que se ordena (opcional)

Ejemplo de petición:
```
GET http://localhost:8080/productos?page=0&size=5&sort=nombre,asc
```
Esto devolverá la primera página con 5 productos, ordenados por `nombre` de forma ascendente.

---

## **6. Respuesta JSON con paginación**
Spring Boot devuelve un JSON con los datos y metadatos de la paginación:

```json
{
    "content": [
        { "id": 1, "nombre": "Producto A", "precio": 100.0 },
        { "id": 2, "nombre": "Producto B", "precio": 200.0 }
    ],
    "pageable": {
        "sort": { "empty": false, "sorted": true, "unsorted": false },
        "offset": 0,
        "pageSize": 5,
        "pageNumber": 0
    },
    "totalPages": 10,
    "totalElements": 50,
    "last": false,
    "first": true,
    "size": 5,
    "number": 0,
    "sort": { "empty": false, "sorted": true, "unsorted": false },
    "numberOfElements": 5,
    "empty": false
}
```

---

## **7. Personalización de la paginación**
Spring Boot permite definir valores predeterminados para la paginación usando `@PageableDefault`:

```java
@GetMapping("/personalizado")
public ResponseEntity<Page<Producto>> obtenerProductosPersonalizados(
    @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
    return ResponseEntity.ok(productoService.obtenerProductosPaginados(pageable));
}
```
Aquí, si el usuario no proporciona valores en la URL, se devuelve una página con 10 elementos ordenados por `nombre`.

---

## **8. Filtrado y paginación combinados**
También se pueden combinar filtros con paginación. Ejemplo de un método que filtra productos por categoría:

```java
@GetMapping("/categoria")
public ResponseEntity<Page<Producto>> obtenerProductosPorCategoria(
    @RequestParam String categoria,
    Pageable pageable) {
    return ResponseEntity.ok(productoRepository.findByCategoria(categoria, pageable));
}
```

Repositorio con el método filtrado:

```java
Page<Producto> findByCategoria(String categoria, Pageable pageable);
```

Ejemplo de petición:
```
GET http://localhost:8080/productos/categoria?categoria=electronica&page=0&size=5
```
