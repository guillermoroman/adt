
En esta tarea vamos a diseñar un pequeño sistema de gestión de recursos humanos donde llevaremos un control de los empleados y los vehículos de la empresa.

## Nombre del proyecto
adt-t2-tarea


## Paquete
Dado que nuestro proyecto trata de gestión de empleados y recursos asignados a los mismos, vamos a crear nuestro proyecto dentro de un paquete con el nombre `hr.management`.

## Jerarquía de archivos
```
/ src
    / main
    / java
	/ hr.management
            / model
                - Employee.java
                - Vehicle.java
            /tools
                - JsonManager.java
                - XMLManager.java
                - ReportGenerator.java
                - EmployeeManager.java
                - Main.java
/data
    - employees.json
    - vehicles.json
    - report.txt
    - newVehicle1.xml
```


## Clases
### Employee
Atributos:
- `dni`: String
- `name`: String.
- `yearHired`: int.
- `vehiclePlate`: String

Métodos:
- `assignVehicleWithPlate`:  Recibe una matrícula que queda asignada al empleado. Si se asigna correctamente, se imprime `"Vehículo con matrícula " + plate + " asignado a " + name`.
- `unassignVehicle`: Elimina el contenido del atributo `VehiclePlate`. Si se elimina correctamente, se imprime `"Vehículo con matrícula " + vehiclePlate + " desasignado de " + name`. De lo contrario, se imprime por pantalla `name + " no tiene un vehículo asignado."`
- constructores, getters, setters y toString (`"Empleado [DNI: " + dni + ", Nombre: " + name + ", Año Contratado: " + yearHired +
                ", Matrícula del Vehículo: " + (vehiclePlate != null ? vehiclePlate : "Ninguno") + "]"`).
### Vehicle
Atributos:
- `plate`: String
- `brand`: String.
- `model`: String.
- `yearBought`: int

Métodos:
- constructores, getters, setters y toString (`"Vehículo [Marca: " + brand + ", Modelo: " + model + ", Matrícula: " + plate +
                ", Año de Compra: " + yearBought + "]"`)

### EmployeeManager
Esta clase pretende hacer operaciones relacionadas con una colección de empleados. Por eso, aunque los métodos trabajen con objetos de la clase `Employee`, no podemos guardarlos dentro de la clase. También contará con la información del arreglo de vehículos ya que los necesitará para poder asignarlos por matrícula.

Atributos:
- `employees`: `ArrayList<Employee>` Lista de empleados gestionados
- `vehicles`: `ArrayList<Vehicle>` Lista de vehículos gestionados

Métodos:
- Constructor con todos los atributos.
- `findEmployeeByDni`: Toma un dni y devuelve un `Employee`.
- `findVehicleByPlate`: Toma una matricula y devuelve un `Vehicle`.
- `assignVehicleToEmployee`: Toma un dni y una matrícula y devuelve *true* si el vehículo se asigna correctamente; *false* si no. Un vehículo solo puede estar asignado a un empleado en un momento dado.
- `unassignVehicleFromEmployee`: Toma un dni y devuelve *true* si el vehículo se desasigna correctamente; *false* si no. Si no encuentra el empleado, imprime _"Empleado no encontrado."_.
- `printAllEmployees` Imprime por pantalla la lista de empleados (sin información sobre vehículos)
- `createEmployeeFolders`: no toma argumentos ni devuelve un valor. El método crea, dentro de la carpeta `data`, la carpeta `employeeDocuments`. Dentro de esta carpeta, el método crea una carpeta por cada empleado que tendrá por nombre su DNI ya que será único. Si tiene éxito creando la carpeta inicial, imprime `"Carpeta 'employeeDocuments' creada."`. De lo contrario, imprime `"Error al crear la carpeta 'employeeDocuments'."`. Después, para cada subcarpeta, si la crea con éxito, imprime `"Carpeta creada para el empleado con DNI: " + employee.getDni()`. De lo contrario, imprime `"Error al crear la carpeta para el empleado: " + employee.getDni()`.
- `removeEmployee`: Acepta un dni que pertenece a un empleado que deberá borrar de la lista actual. Deberá ocuparse de eliminar la carpeta asociada a este empleado que se encontrará dentro de la carpeta `data`. Si logra eliminar el empleado, imprime `"Empleado con DNI " + dni + " eliminado de la lista."`. Dado que también tiene que eliminar la carpeta, imprimirá `"Carpeta del empleado con DNI " + dni + " eliminada."` o `"La carpeta del empleado con DNI " + dni + " no existe."` en su caso.

### JsonManager
Atributos:
- `objectMapper`: atributo estático inicializado en la propia declaración com un nuevo `ObjectMapper`.

Métodos:
- `saveEmployeesToJson`: Toma una lista de empleados (`ArrayList<Employee>`) y una ruta de archivo, y guarda la lista en un archivo JSON. Devuelve true si la operación de guardado fue exitosa, y false si ocurre algún error.
- `loadEmployeesFromJson`: Toma una ruta de archivo y devuelve un `ArrayList<Employee>`, que contiene la lista de empleados deserializados desde el archivo JSON. Si ocurre algún error (por ejemplo, si el archivo no existe o está corrupto), devuelve un .empty().
- `saveVehiclesToJson`: Toma una lista de vehículos (`ArrayList<Vehicle>`) y una ruta de archivo, y guarda la lista en un archivo JSON. Devuelve true si la operación de guardado fue exitosa, y false si ocurre algún error.
- `loadVehiclesFromJson`: Toma una ruta de archivo y devuelve un `ArrayList<Vehicle>`, que contiene la lista de vehículos deserializados desde el archivo JSON.


### ReportGenerator
No tiene atributos.
Métodos:
- `generateReport`: Método recibe una lista de empleados, una lista de vehículos y un String con la ruta a un archivo de salida con extensión txt. Genera un informe con los datos de los empleados que tienen un vehículo asignado y los datos de su vehículo.

Ejemplo de salida en archivo informe.txt:
```
Informe de empleados con vehículos asignados
===========================================
Empleado:
  DNI: 12345678A
  Nombre: James Bond
  Año Contratado: 2006
  Vehículo:
    Marca: Volvo
    Modelo: S50
    Matrícula: 0007-BND
    Año: 2019
===========================================
Empleado:
  DNI: 23456789C
  Nombre: Walter Smith
  Año Contratado: 2019
  Vehículo:
    Marca: Ford
    Modelo: Fiesta
    Matrícula: 1234-XYZ
    Año: 2015
===========================================
```


### XMLManager
Cada vez que nuestra empresa compra un vehículo, recibe un XML del concesionario con información relevante del mismo. Cuando añadimos el vehículo a nuestros datos, debemos generar un nuevo objeto `Vehicle` desde el XML
- `readVehicleFromXml`: Acepta una ruta de archivo y devuelve un objeto de tipo `Vehicle`.


## Archivos

`vehicles.json`:
```json
[ {  
  "matricula" : "0007-BND",  
  "marca" : "Volvo",  
  "modelo" : "S50",  
  "añoCompra" : 2019  
}, {  
  "matricula" : "1234-XYZ",  
  "marca" : "Ford",  
  "modelo" : "Fiesta",  
  "añoCompra" : 2015  
}, {  
  "matricula" : "5632-WCT",  
  "marca" : "Toyota",  
  "modelo" : "Rav 4",  
  "añoCompra" : 2015  
}, {  
  "matricula" : "6789-ADT",  
  "marca" : "Volkswagen",  
  "modelo" : "Golf",  
  "añoCompra" : 2024  
} ]
```

`employees.json`:
```json
[ {  
  "dni" : "12345678A",  
  "nombre" : "James Bond",  
  "añoContratado" : 2006,  
  "matriculaVehiculo" : "0007-BND"  
}, {  
  "dni" : "87654321B",  
  "nombre" : "John Doe",  
  "añoContratado" : 2018,  
  "matriculaVehiculo" : "6789-ADT"  
}, {  
  "dni" : "23456789C",  
  "nombre" : "Walter Smith",  
  "añoContratado" : 2019,  
  "matriculaVehiculo" : null  
}, {  
  "dni" : "34567890D",  
  "nombre" : "Kathy Spencer",  
  "añoContratado" : 2022,  
  "matriculaVehiculo" : null  
} ]
```

`newVehicle.xml`:
```xml
<vehicle>
    <plate>6789-ADT</plate>
    <brand>Volkswagen</brand>
    <model>Golf</model>
    <yearBought>2024</yearBought>
</vehicle>
```

## Pruebas unitarias
**Para la corrección del ejercicio, además de observar el comportamiento de main, se ha creado una batería de pruebas unitarias que deberían superarse si se ha respetado la funcionalidad indicada, así como los nombres, parámetros y valores de retorno de los métodos propuestos.**

## Main

A continuación, el código de un main de ejemplo que pone a prueba la funcionalidad del programa. Se recomienda tener este main intacto y, en caso de que os sea necesario, creéis otra clase con método main en la que podéis hacer pruebas adicionales.

```java
try {
            // 1. Cargar datos de empleados y vehículos desde JSON
            ArrayList<Employee> employees = JsonManager.loadEmployeesFromJson("data/employees.json");
            ArrayList<Vehicle> vehicles = JsonManager.loadVehiclesFromJson("data/vehicles.json");

            // 2. Añadir un vehículo desde el archivo newVehicle1.xml
            Vehicle newVehicle = XmlManager.readVehicleFromXml("data/newVehicle1.xml");
            vehicles.add(newVehicle);
            System.out.println("Nuevo vehículo añadido desde XML: " + newVehicle);

            // 3. Asignar el nuevo vehículo al empleado con DNI 87654321B
            EmployeeManager employeeManager = new EmployeeManager( employees, vehicles);
            boolean assigned = employeeManager.assignVehicleToEmployee("87654321B", newVehicle.getPlate());
            if (assigned) {
                System.out.println("Vehículo asignado correctamente al empleado con DNI 87654321B.");
            } else {
                System.out.println("No se pudo asignar el vehículo.");
            }

            // 4. Desasignar el vehículo del empleado Walter Smith
            boolean unassigned = employeeManager.unassignVehicleFromEmployee("23456789C");
            if (unassigned) {
                System.out.println("Vehículo desasignado correctamente del empleado Walter Smith.");
            } else {
                System.out.println("No se pudo desasignar el vehículo del empleado Walter Smith.");
            }

            // 5. Generar un informe en formato TXT utilizando ReportGenerator
            ReportGenerator.generateReport(employees, vehicles, "data/informe.txt");
            System.out.println("Informe generado exitosamente en 'data/informe.txt'.");

            // 6. Imprimir todos los empleados con el método printAllEmployees
            System.out.println("Lista de empleados:");
            employeeManager.printAllEmployees();

            // Operación adicional: Guardar de nuevo los datos actualizados en los archivos JSON
            JsonManager.saveEmployeesToJson(employees, "data/employees.json");
            JsonManager.saveVehiclesToJson(vehicles, "data/vehicles.json");
            System.out.println("Datos actualizados guardados en archivos JSON.");

        } catch (IOException e) {
            e.printStackTrace();
        }

```

Salida esperada para el código anterior:

```
Nuevo vehículo añadido desde XML: Vehículo [Marca: Volkswagen, Modelo: Golf, Matrícula: 6789-ADT, Año de Compra: 2024]
Vehículo con matrícula 6789-ADT asignado a John Doe
Vehículo asignado correctamente al empleado con DNI 87654321B.
Walter Smith no tiene un vehículo asignado.
Vehículo desasignado correctamente del empleado Walter Smith.
Informe generado exitosamente en 'data/informe.txt'.
Lista de empleados:
Empleado [DNI: 12345678A, Nombre: James Bond, Año Contratado: 2006, Matrícula del Vehículo: 0007-BND]
Empleado [DNI: 87654321B, Nombre: John Doe, Año Contratado: 2018, Matrícula del Vehículo: 6789-ADT]
Empleado [DNI: 23456789C, Nombre: Walter Smith, Año Contratado: 2019, Matrícula del Vehículo: Ninguno]
Empleado [DNI: 34567890D, Nombre: Kathy Spencer, Año Contratado: 2022, Matrícula del Vehículo: Ninguno]
Datos actualizados guardados en archivos JSON.
```


---
