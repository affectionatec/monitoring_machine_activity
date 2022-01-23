package uk.ac.cardiff.mma.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.cardiff.mma.application.DTO.GasDTO;
import uk.ac.cardiff.mma.application.DTO.GasDeliveryDTO;
import uk.ac.cardiff.mma.application.DTO.UserEquipmentTrainedDTO;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/* Run unit tests in the spring framework via the @SpringBootTest class annotation and the
** @Test annotation. Use various assert… methods to check that the results are as expected. */
@SpringBootTest
public class UnitTestsForJC {

    @Test
    public void GasDTOTest() {
        GasDTO gasDTO = new GasDTO(1, "NH3", 20, "litres", 5, "Be carefully.", "Second floor", "Compressed", "New Gas");
        assertEquals(gasDTO.getName(), "NH3");
        assertEquals(gasDTO.getStorage(), 20);
        assertEquals(gasDTO.getBottleNum(), 5);
        assertEquals(gasDTO.getCoshh(), "Be carefully.");
        assertEquals(gasDTO.getLocation(), "Second floor");
        assertEquals(gasDTO.getHazardLevel(), "Compressed");
        assertEquals(gasDTO.getComments(), "New Gas");
    }

    @Test
    public void GasDeliveryDTOTest() {
        GasDeliveryDTO gasDeliveryDTO = new GasDeliveryDTO(1, 1, new Date(2021,9,1), new Date(2022,9,1), 8, "George");
        assertEquals(gasDeliveryDTO.getDeliveryDate(), new Date(2021,9,1));
        assertEquals(gasDeliveryDTO.getExpiryDate(), new Date(2022,9,1));
        assertEquals(gasDeliveryDTO.getDistributionWeight(), 8);
        assertEquals(gasDeliveryDTO.getDeliveryStaff(), "George");
    }

    @Test
    public void UserEquipmentTrainedDTOTest() {
        UserEquipmentTrainedDTO userEquipmentTrainedDTO = new UserEquipmentTrainedDTO(1, "George", "2021-09-01");
        assertEquals(userEquipmentTrainedDTO.getUsername(), "George");
        assertEquals(userEquipmentTrainedDTO.getDateTrained(), "2021-09-01");
    }

}
