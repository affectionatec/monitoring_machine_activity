package uk.ac.cardiff.mma.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ChemicalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
    }

    @Before
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //unit test with h2 database
    @Test
    @WithMockUser(roles = "ADMIN")
    public void queryAllChemicalOnChemicalPage() throws Exception{
        this.mockMvc.perform(get("/admin/chemicalReport"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("si")))
                .andExpect(content().string(containsString("Chemical Name")))
                .andExpect(content().string(containsString("View")));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void viewChemicalDeliveryDetailTest() throws Exception{
        this.mockMvc.perform(get("/admin/viewDelivery/15"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().methodName("queryChemicalDeliveryDetailByID"))
                .andExpect(model().hasNoErrors())
                .andExpect(content().string(containsString("Add A New Chemical Delivery Record")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addAChemicalTest() throws Exception{
        this.mockMvc.perform(post("/admin/addChemical")
                .param("chemicalName","H")
                .param("level","Toxic")
                .param("location","Third Floor")
                .param("storage", "69")
                .param("bottleNum", "5")
                .param("unit","L"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/chemicalReport"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteAChemicalDeliveryTest() throws Exception{
        this.mockMvc.perform(get("/admin/deleteDeliveryRecord/1/si"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/viewDelivery/{id}"));
    }



    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }



}
