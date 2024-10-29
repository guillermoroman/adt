package hr.resources.tools;

import hr.resources.model.Employee;
import hr.resources.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeManagerTest {

    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("12345678A", "James Bond", 2006);
        assertEquals("12345678A", employee.getDni());
        assertEquals("James Bond", employee.getName());
        assertEquals(2006, employee.getYearHired());
        assertNull(employee.getVehiclePlate());  // Por defecto no tiene vehículo asignado
    }

    @Test
    void testVehicleCreation() {
        Vehicle vehicle = new Vehicle("0007-BND", "Volvo", "S50", 2019);
        assertEquals("0007-BND", vehicle.getPlate());
        assertEquals("Volvo", vehicle.getBrand());
        assertEquals("S50", vehicle.getModel());
        assertEquals(2019, vehicle.getYearBought());
    }

    @Test
    void testAssignVehicleToEmployee() {
        Employee employee = new Employee("12345678A", "James Bond", 2006);
        Vehicle vehicle = new Vehicle("0007-BND", "Volvo", "S50", 2019);
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        EmployeeManager manager = new EmployeeManager(employees, vehicles);

        boolean success = manager.assignVehicleToEmployee("12345678A", "0007-BND");
        assertTrue(success);
        assertEquals("0007-BND", employee.getVehiclePlate());  // La matrícula debe ser asignada correctamente
    }

    @Test
    void testUnassignVehicleFromEmployee() {
        Employee employee = new Employee("12345678A", "James Bond", 2006);
        employee.assignVehicleWithPlate("0007-BND");

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        EmployeeManager manager = new EmployeeManager(employees, new ArrayList<>());

        boolean success = manager.unassignVehicleFromEmployee("12345678A");
        assertTrue(success);
        assertNull(employee.getVehiclePlate());  // El vehículo debe ser desasignado
    }

    @Test
    void testFindEmployeeByDni() {
        Employee employee1 = new Employee("12345678A", "James Bond", 2006);
        Employee employee2 = new Employee("87654321B", "John Doe", 2018);

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        EmployeeManager manager = new EmployeeManager(employees, new ArrayList<>());

        Employee foundEmployee = manager.findEmployeeByDni("12345678A");
        assertNotNull(foundEmployee);
        assertEquals("James Bond", foundEmployee.getName());

        Employee notFoundEmployee = manager.findEmployeeByDni("99999999C");
        assertNull(notFoundEmployee);  // El empleado no existe, por lo tanto debe ser null
    }

    @Test
    void testAssignVehicleToNonExistingEmployee() {
        Employee employee = new Employee("12345678A", "James Bond", 2006);
        Vehicle vehicle = new Vehicle("0007-BND", "Volvo", "S50", 2019);
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        EmployeeManager manager = new EmployeeManager(employees, vehicles);

        boolean success = manager.assignVehicleToEmployee("99999999C", "0007-BND");  // El empleado no existe
        assertFalse(success);
    }

    @Test
    void testAssignNonExistingVehicleToEmployee() {
        Employee employee = new Employee("12345678A", "James Bond", 2006);
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        ArrayList<Vehicle> vehicles = new ArrayList<>();  // No hay vehículos disponibles

        EmployeeManager manager = new EmployeeManager(employees, vehicles);

        boolean success = manager.assignVehicleToEmployee("12345678A", "0007-BND");  // El vehículo no existe
        assertFalse(success);
    }

    // Método para crear las carpetas de documentos para cada empleado
    public void createEmployeeFolders(List<Employee> employees) {
        // Crear la carpeta 'employeeDocuments' si no existe
        File employeeDocumentsDir = new File("data/employeeDocuments");
        if (!employeeDocumentsDir.exists()) {
            if (employeeDocumentsDir.mkdirs()) {
                System.out.println("Carpeta 'employeeDocuments' creada.");
            } else {
                System.out.println("Error al crear la carpeta 'employeeDocuments'.");
            }
        }

        // Crear una subcarpeta para cada empleado utilizando el DNI
        for (Employee employee : employees) {
            File employeeFolder = new File(employeeDocumentsDir, employee.getDni());
            if (!employeeFolder.exists()) {
                if (employeeFolder.mkdir()) {
                    System.out.println("Carpeta creada para el empleado con DNI: " + employee.getDni());
                } else {
                    System.out.println("Error al crear la carpeta para el empleado: " + employee.getDni());
                }
            }
        }
    }

    // Test para eliminar empleado y su carpeta de documentos
    @Test
    void testRemoveEmployee() throws IOException {
        // Crear lista de empleados simulada
        ArrayList<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee("12345678A", "James Bond", 2006);
        Employee emp2 = new Employee("87654321B", "John Doe", 2018);
        employees.add(emp1);
        employees.add(emp2);

        // Crear lista vacía de vehículos
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        // Crear un directorio simulado para cada empleado
        File employeeDocumentsDir = new File("data/employeeDocuments");
        if (!employeeDocumentsDir.exists()) {
            employeeDocumentsDir.mkdirs();
        }
        File emp1Folder = new File(employeeDocumentsDir, emp1.getDni());
        emp1Folder.mkdir();  // Crear la carpeta del empleado emp1
        File emp2Folder = new File(employeeDocumentsDir, emp2.getDni());
        emp2Folder.mkdir();  // Crear la carpeta del empleado emp2

        // Verificar que los directorios existen antes de la eliminación
        assertTrue(emp1Folder.exists());
        assertTrue(emp2Folder.exists());

        // Crear el EmployeeManager
        EmployeeManager manager = new EmployeeManager(employees, vehicles);

        // Eliminar el empleado emp1
        manager.removeEmployee(emp1.getDni());

        // Verificar que el empleado fue eliminado de la lista
        assertEquals(1, employees.size());
        assertNull(employees.stream().filter(e -> e.getDni().equals(emp1.getDni())).findFirst().orElse(null));

        // Verificar que la carpeta de emp1 ha sido eliminada
        assertFalse(emp1Folder.exists());

        // Verificar que la carpeta de emp2 aún existe
        assertTrue(emp2Folder.exists());
    }

    // Test para eliminar recursivamente archivos y carpetas
    @Test
    void testDeleteFolder() throws IOException {
        // Crear un directorio temporal y algunos archivos
        File folder = new File("data/testFolder");
        folder.mkdirs();

        File file1 = new File(folder, "file1.txt");
        File file2 = new File(folder, "file2.txt");
        Files.createFile(file1.toPath());
        Files.createFile(file2.toPath());

        // Crear subdirectorio y un archivo dentro de él
        File subFolder = new File(folder, "subFolder");
        subFolder.mkdir();
        File subFile = new File(subFolder, "subFile.txt");
        Files.createFile(subFile.toPath());

        // Verificar que los archivos y carpetas existen
        assertTrue(folder.exists());
        assertTrue(file1.exists());
        assertTrue(file2.exists());
        assertTrue(subFolder.exists());
        assertTrue(subFile.exists());

        // Crear el EmployeeManager (aunque solo usaremos deleteFolder en este test)
        EmployeeManager manager = new EmployeeManager(new ArrayList<>(), new ArrayList<>());

        // Eliminar el directorio de forma recursiva
        manager.deleteFolder(folder);

        // Verificar que ha sido eliminado
        assertFalse(folder.exists());
        assertFalse(file1.exists());
        assertFalse(file2.exists());
        assertFalse(subFolder.exists());
        assertFalse(subFile.exists());
    }
}