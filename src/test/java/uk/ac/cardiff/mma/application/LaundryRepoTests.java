package uk.ac.cardiff.mma.application;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cardiff.mma.application.DTO.LaundryDetailDTO;
import uk.ac.cardiff.mma.application.repository.LaundryRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class LaundryRepoTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LaundryRepository laundryRepository;


    //  Component test using a mockMVC but mocking the repository response (Note the @MockBean)
    //  This only tests the get controller and the view layers
    //    This does NOT check the repo connection to the database.
    @Test
    @WithMockUser(roles="ADMIN")
    public void getLaundryInfoTest() throws Exception {

        LaundryDetailDTO laundryDetailDTO = new LaundryDetailDTO("1","1001","Clean","good");

        given(this.laundryRepository.findAllLaundryInfo()).willReturn(Arrays.asList(laundryDetailDTO));
        this.mockMvc.perform(get("/admin/laundryInfo")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1001")))
                .andExpect(content().string(containsString("Clean")))
                .andExpect(content().string(containsString("good")));

        LaundryDetailDTO item = new LaundryDetailDTO("Boots","off site being laundered","in use",1);
        given(this.laundryRepository.findAllBootsInfo_byBarcode("1001")).willReturn(Arrays.asList(item));


    }
}
