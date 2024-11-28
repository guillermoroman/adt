## Crea un proyecto maven
Incluye como dependencias:
- MariaDB
- Hibernate
- JUnit

## Configuración de Hibernate

#### a) `hibernate.cfg.xml`

Crea un archivo `hibernate.cfg.xml` en `src/main/resources` con la configuración de conexión:

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<!DOCTYPE hibernate-configuration PUBLIC  
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"  
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">  
<hibernate-configuration>  
    <session-factory>
            
	    <!-- Database connection settings -->  
        <property name="hibernate.connection.driver_class">org.mariadb.jdbc.Driver</property>  
        <property name="hibernate.connection.url">jdbc:mariadb://localhost:3306/adt_t4_demo1</property>  
        <property name="hibernate.connection.username">root</property>  
        <property name="hibernate.connection.password"></property>  
  
        <!-- Hibernate settings -->  
        <property name="hibernate.dialect">org.hibernate.dialect.MariaDBDialect</property>  
        <property name="hibernate.show_sql">true</property>  
        <property name="hibernate.format_sql">true</property>  
        <property name="hibernate.use_sql_comments">true</property>  
        <property name="hibernate.hbm2ddl.auto">update</property>  
  
        <!-- Mappings -->  
        <mapping class="com.example.model.User"/>  
    </session-factory>
</hibernate-configuration>
```

Parámetros utilizados para la configuración específica de Hibernate:

#### Dialecto
- **`hibernate.dialect`**: Especifica el dialecto que Hibernate usará para generar las consultas SQL específicas del sistema de base de datos. En este caso, se usa `MariaDBDialect`, optimizado para MariaDB.

#### Opciones de depuración
- **`hibernate.show_sql`**: Si está en `true`, Hibernate mostrará en la consola las consultas SQL que ejecuta.
- **`hibernate.format_sql`**: Si está en `true`, las consultas SQL mostradas en la consola estarán formateadas, facilitando su lectura.
- **`hibernate.use_sql_comments`**: Si está en `true`, Hibernate incluirá comentarios en las consultas SQL generadas, lo que ayuda a comprender mejor su propósito.

#### Modo de gestión del esquema
- **`hibernate.hbm2ddl.auto`**: Define cómo Hibernate manejará el esquema de la base de datos. Valores comunes:
    - **`create`**: Borra y recrea el esquema cada vez que se inicia Hibernate.
    - **`update`**: Actualiza el esquema existente sin borrar datos (útil para desarrollo).
    - **`validate`**: Solo valida el esquema existente, sin realizar cambios.
    - **`none`** o no configurado: No realiza ninguna acción en el esquema.

#### b) Clase `HibernateUtil`
Crea una clase para manejar las sesiones de Hibernate:

```java
package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
```


## `HibernateUtil.java`

## `hibernate.cfg.xml`

## Creamos una clase


Aquí tienes un pequeño proyecto que muestra el funcionamiento básico de Hibernate con MariaDB y un test unitario para probar la funcionalidad. El proyecto usará Maven como gestor de dependencias y JUnit para las pruebas.

---

### **1. Configuración del proyecto**

#### a) `pom.xml`

Añade las dependencias necesarias en el archivo `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>hibernate-mariadb-example</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- Hibernate -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.2.0.Final</version>
        </dependency>
        <!-- MariaDB driver -->
        <dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
            <version>3.1.2</version>
        </dependency>
        <!-- JUnit for testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

---

### **2. Estructura del proyecto**

```
src/main/java/com/example
    ├── App.java
    ├── model/
    │   └── User.java
    ├── util/
    │   └── HibernateUtil.java
src/test/java/com/example
    └── UserTest.java
```

---

### **3. Configuración de Hibernate**


---

### **4. Modelo de datos**

Crea una clase `User` como entidad:

```java
package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

---

### **5. Clase principal**

Crea la clase `App` para interactuar con Hibernate:

```java
package com.example;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;

public class App {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Crear y guardar un usuario
        User user = new User();
        user.setName("Juan Pérez");
        user.setEmail("juan.perez@example.com");
        session.save(user);

        session.getTransaction().commit();
        session.close();

        System.out.println("Usuario guardado: " + user.getName());
        HibernateUtil.shutdown();
    }
}
```

---

### **6. Prueba unitaria**

Crea una prueba con JUnit para comprobar la funcionalidad:

```java
package com.example;

import com.example.model.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    public void testSaveUser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Crear y guardar un usuario
        User user = new User();
        user.setName("Test User");
        user.setEmail("test.user@example.com");
        session.save(user);

        session.getTransaction().commit();
        session.close();

        // Verificar que el usuario tiene un ID asignado
        assertNotNull(user.getId());
    }
}
```

---

### **7. Configura la base de datos**

Este paso ya no es necesario con Hibernate; Hibernate crea automáticamente una tabla que cumpla con los requisitos necesarios para poder persistir la clase creada.
```sql
-- Crear la base de datos
CREATE DATABASE adt_t4_demo1;

-- Usar la base de datos recién creada
USE adt_t4_demo1;

-- Crear la tabla users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Clave primaria con auto-incremento
    name VARCHAR(255) NOT NULL,           -- Campo para el nombre, obligatorio
    email VARCHAR(255) NOT NULL UNIQUE    -- Campo para el email, obligatorio y único
);
```
