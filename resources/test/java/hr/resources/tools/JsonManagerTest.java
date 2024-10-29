package hr.resources.tools;

import hr.resources.model.Employee;
import hr.resources.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonManagerTest {
    @Test
    void testSaveEmployeesToJson() throws Exception {
        // Crear lista de empleados de prueba
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("12345678A", "James Bond", 2006));
        employees.add(new Employee("87654321B", "John Doe", 2018));

        // Crear un archivo temporal
        File tempFile = File.createTempFile("employees", ".json");
        tempFile.deleteOnExit();  // El archivo será eliminado al final de la prueba

        // Escribir la lista de empleados en el archivo JSON
        JsonManager.saveEmployeesToJson(employees, tempFile.getAbsolutePath());

        // Verificar que el archivo existe y no está vacío
        assertTrue(tempFile.exists());
        assertTrue(Files.readString(tempFile.toPath()).length() > 0);
    }

    @Test
    void testLoadEmployeesFromJson() throws Exception {
        // JSON de prueba con empleados
        String jsonContent = """
            [
                {"dni": "12345678A", "nombre": "James Bond", "añoContratado": 2006, "matriculaVehiculo": "0007-BND"},
                {"dni": "87654321B", "nombre": "John Doe", "añoContratado": 2018, "matriculaVehiculo": null}
            ]
        """;

        // Crear un archivo temporal y escribir el JSON de prueba en él
        File tempFile = File.createTempFile("employees", ".json");
        tempFile.deleteOnExit();
        Files.writeString(tempFile.toPath(), jsonContent);

        // Leer los empleados desde el archivo JSON
        List<Employee> employees = JsonManager.loadEmployeesFromJson(tempFile.getAbsolutePath());

        // Verificar que se cargaron correctamente
        assertEquals(2, employees.size());
        assertEquals("12345678A", employees.get(0).getDni());
        assertEquals("James Bond", employees.get(0).getName());
        assertEquals(2006, employees.get(0).getYearHired());
        assertEquals("0007-BND", employees.get(0).getVehiclePlate());
        assertNull(employees.get(1).getVehiclePlate());
    }

    @Test
    void testSaveVehiclesToJson() throws Exception {
        // Crear lista de vehículos de prueba
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("0007-BND", "Volvo", "S50", 2019));
        vehicles.add(new Vehicle("1234-XYZ", "Ford", "Fiesta", 2015));

        // Crear un archivo temporal
        File tempFile = File.createTempFile("vehicles", ".json");
        tempFile.deleteOnExit();

        // Escribir la lista de vehículos en el archivo JSON
        JsonManager.saveVehiclesToJson(vehicles, tempFile.getAbsolutePath());

        // Verificar que el archivo existe y no está vacío
        assertTrue(tempFile.exists());
        assertTrue(Files.readString(tempFile.toPath()).length() > 0);
    }

    @Test
    void testLoadVehiclesFromJson() throws Exception {
        // JSON de prueba con vehículos
        String jsonContent = """
            [
                {"matricula": "0007-BND", "marca": "Volvo", "modelo": "S50", "añoCompra": 2019},
                {"matricula": "1234-XYZ", "marca": "Ford", "modelo": "Fiesta", "añoCompra": 2015}
            ]
        """;

        // Crear un archivo temporal y escribir el JSON de prueba en él
        File tempFile = File.createTempFile("vehicles", ".json");
        tempFile.deleteOnExit();
        Files.writeString(tempFile.toPath(), jsonContent);

        // Leer los vehículos desde el archivo JSON
        List<Vehicle> vehicles = JsonManager.loadVehiclesFromJson(tempFile.getAbsolutePath());

        // Verificar que se cargaron correctamente
        assertEquals(2, vehicles.size());
        assertEquals("0007-BND", vehicles.get(0).getPlate());
        assertEquals("Volvo", vehicles.get(0).getBrand());
        assertEquals("S50", vehicles.get(0).getModel());
        assertEquals(2019, vehicles.get(0).getYearBought());
        assertEquals("1234-XYZ", vehicles.get(1).getPlate());
    }
}
