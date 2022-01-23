package uk.ac.cardiff.mma.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import uk.ac.cardiff.mma.application.DTO.ChemicalDTO;
import uk.ac.cardiff.mma.application.equipment.entity.ConsumableDTO;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ConsumableTests {



    @Test
    public void consumableDTOTest(){

        ConsumableDTO consumableDTO = new ConsumableDTO(1l,"name","description","type",100);

        assertEquals(consumableDTO.getName(),"name");
        assertEquals(consumableDTO.getDescription(),"description");
        assertEquals(consumableDTO.getType(),"type");
        assertEquals(consumableDTO.getStock(),100);

    }




}
