package uk.ac.cardiff.mma.application;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.cardiff.mma.application.DTO.ChemicalDTO;
import uk.ac.cardiff.mma.application.DTO.ChemicalDeliveryDTO;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ChemicalDTOTest {



    @Test
    public void chemicalDTO(){
        ChemicalDTO chemicalDTO = new ChemicalDTO(1,"si", 68, "Toxic", "ground floor", "kg",2);

        assertEquals(chemicalDTO.getChemicalID(),1);
        assertEquals(chemicalDTO.getChemicalName(),"si");
        assertEquals(chemicalDTO.getLevel(),"Toxic");
        assertEquals(chemicalDTO.getLocation(),"ground floor");
        Assertions.assertEquals(chemicalDTO.getStorage(),68);
        assertEquals(chemicalDTO.getUnit(),"kg");
        assertEquals(chemicalDTO.getBottleNum(),2);

    }

    @Test
    public void chemicalDeliveryDTO(){

        ChemicalDeliveryDTO chemicalDeliveryDTO = new ChemicalDeliveryDTO(1,"si","2021-09-04","2030-09-04",68,"kg");
        assertEquals(chemicalDeliveryDTO.getId(),1);
        assertEquals(chemicalDeliveryDTO.getChemicalName(),"si");
        assertEquals(chemicalDeliveryDTO.getDeliveryDate(),"2021-09-04");
        assertEquals(chemicalDeliveryDTO.getExpiry(),"2030-09-04");
        Assertions.assertEquals(chemicalDeliveryDTO.getWeight(),68);
        assertEquals(chemicalDeliveryDTO.getUnit(),"kg");


    }

}
