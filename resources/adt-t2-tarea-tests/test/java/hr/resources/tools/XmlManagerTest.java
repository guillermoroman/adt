package hr.resources.tools;

import hr.resources.model.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Files;

class XmlManagerTest {

    @Test
    void testReadVehicleFromXml() throws Exception {
        // Crear contenido XML de prueba
        String xmlContent = """
            <vehicle>
                <plate>6789-ADT</plate>
                <brand>Volkswagen</brand>
                <model>Golf</model>
                <yearBought>2024</yearBought>
            </vehicle>
        """;

        // Crear un archivo temporal y escribir el XML de prueba en él
        File tempFile = File.createTempFile("vehicle", ".xml");
        tempFile.deleteOnExit();
        Files.writeString(tempFile.toPath(), xmlContent);

        // Leer el vehículo desde el archivo XML
        Vehicle vehicle = XmlManager.readVehicleFromXml(tempFile.getAbsolutePath());

        // Verificar que los datos se cargaron correctamente
        assertEquals("6789-ADT", vehicle.getPlate());
        assertEquals("Volkswagen", vehicle.getBrand());
        assertEquals("Golf", vehicle.getModel());
        assertEquals(2024, vehicle.getYearBought());
    }
}