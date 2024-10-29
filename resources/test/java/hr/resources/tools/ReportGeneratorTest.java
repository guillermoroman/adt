package hr.resources.tools;

import hr.resources.model.Employee;
import hr.resources.model.Vehicle;
import hr.resources.tools.ReportGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    void testGenerateReport() throws Exception {
        // Crear lista de empleados de prueba
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = new Employee("12345678A", "James Bond", 2006);
        emp1.assignVehicleWithPlate("0007-BND");
        Employee emp2 = new Employee("87654321B", "John Doe", 2018);
        Employee emp3 = new Employee("23456789C", "Walter Smith", 2019);
        emp3.assignVehicleWithPlate("1234-XYZ");

        employees.add(emp1); // James Bond tiene un vehículo
        employees.add(emp2); // John Doe no tiene un vehículo
        employees.add(emp3); // Walter Smith tiene un vehículo

        // Crear lista de vehículos de prueba
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("0007-BND", "Volvo", "S50", 2019));
        vehicles.add(new Vehicle("1234-XYZ", "Ford", "Fiesta", 2015));

        // Crear un archivo temporal para el informe
        File tempFile = File.createTempFile("informe", ".txt");
        tempFile.deleteOnExit();  // El archivo será eliminado al final de la prueba

        // Generar el informe utilizando el ReportGenerator
        ReportGenerator.generateReport(employees, vehicles, tempFile.getAbsolutePath());

        // Verificar que el archivo de informe fue generado y no está vacío
        assertTrue(tempFile.exists());
        String reportContent = Files.readString(tempFile.toPath());
        assertFalse(reportContent.isEmpty());

        // Verificar que el informe contiene a James Bond y su vehículo
        assertTrue(reportContent.contains("James Bond"));
        assertTrue(reportContent.contains("0007-BND"));
        assertTrue(reportContent.contains("Volvo"));
        assertTrue(reportContent.contains("S50"));
        assertTrue(reportContent.contains("2019"));

        // Verificar que el informe contiene a Walter Smith y su vehículo
        assertTrue(reportContent.contains("Walter Smith"));
        assertTrue(reportContent.contains("1234-XYZ"));
        assertTrue(reportContent.contains("Ford"));
        assertTrue(reportContent.contains("Fiesta"));
        assertTrue(reportContent.contains("2015"));

        // Verificar que John Doe, que no tiene vehículo, no aparece en el informe
        assertFalse(reportContent.contains("John Doe"));
    }
}