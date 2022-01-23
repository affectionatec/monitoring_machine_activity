package uk.ac.cardiff.mma.application;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class HomepageDTOTest {

    @Test
    public void OccupiedEquipmentDTOTest(){
        OccupiedEquipmentDTO occupiedEquipmentDTO = new OccupiedEquipmentDTO(1,"ICP1",
                "Junwei","2021-09-04","1",12,"12");

        assertEquals(occupiedEquipmentDTO.getId(),1);
        assertEquals(occupiedEquipmentDTO.getEquipmentName(),"ICP1");
        assertEquals(occupiedEquipmentDTO.getUserName(),"Junwei");
        assertEquals(occupiedEquipmentDTO.getBookedDate(),"2021-09-04");
        assertEquals(occupiedEquipmentDTO.getTime(),"1");
        assertEquals(occupiedEquipmentDTO.getUsed(),12);
        assertEquals(occupiedEquipmentDTO.getProject_code(),"12");

    }

}
