package uk.ac.cardiff.mma.application;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cardiff.mma.application.DTO.OccupiedEquipmentDTO;
import uk.ac.cardiff.mma.application.repository.EquipmentUsageRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class EquipmentUsageRepoTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentUsageRepository equipmentUsageRepository;


    //  Component test using a mockMVC but mocking the repository response (Note the @MockBean)
    //  This only tests the get controller and the view layers
    //    This does NOT check the repo connection to the database.
    @Test
    @WithMockUser(roles="ADMIN")
    public void getQueryUsedEquipmentTest() throws Exception {

        OccupiedEquipmentDTO volunteerDTO = new OccupiedEquipmentDTO(1,"TEST_EQUIPMENT","TEST_USER","2021-09-20","6");

        given(this.equipmentUsageRepository.queryUsedEquipment()).willReturn(Arrays.asList(volunteerDTO));
        this.mockMvc.perform(get("/admin/equipmentUsageList")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("TEST_EQUIPMENT")))
                .andExpect(content().string(containsString("TEST_USER")))
                .andExpect(content().string(containsString("2021-09-20")));

    }
}
