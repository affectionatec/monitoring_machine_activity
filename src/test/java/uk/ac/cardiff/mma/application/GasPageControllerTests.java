package uk.ac.cardiff.mma.application;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class GasPageControllerTests {

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

    // Unit test using a mock MVC.
    @Test
    @WithMockUser(roles="ADMIN")
    public void whenGetGasViewThenDisplayGasListView() throws Exception {
        this.mockMvc.perform(get("/admin/gasDetail"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CO2")))
                .andExpect(content().string(containsString("Deal with accidents and emergencies.")))
                .andExpect(content().string(containsString("View")))
                .andExpect(content().string(containsString("Edit")))
                .andExpect(content().string(containsString("Delete")));
    }

    // Request access get method, the url path must contain param id.
    @Test
    @WithMockUser(roles="ADMIN")
    public void testViewGasDeliveryDataByIdPage() throws Exception {
        this.mockMvc.perform(get("/admin/view")
                .param("id", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().methodName("queryGasDetailByID")) // Verify the execution of the controller method name
                .andExpect(model().hasNoErrors()) // Verify that the page has no errors
                .andExpect(content().string(containsString("Current Gas Name")));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testAddNewGasByNamePage() throws Exception {
        this.mockMvc.perform(get("/admin/Site/AddGas")
                .param("name", "NH3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("gasName", "NH3"))
                .andExpect(view().name("AddGasForm.html"))
                .andExpect(content().string(containsString("Add new Gas")))
                .andExpect(content().string(containsString("NH3")));
    }

    // Tests the post controller for AddGas and will check it returns the all gas details page with a repo created response.
    @Test
    @WithMockUser(roles="ADMIN")
    public void testAddNewGasFunction() throws Exception {
        this.mockMvc.perform(post("/admin/addGas")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Ne")
                .param("storage", "0")
                .param("unit", "litres")
                .param("bottleNum", "3")
                .param("coshh", "Carry out COSHH risk assessments.")
                .param("location", "Second floor")
                .param("hazardLevel", "Compressed")
                .param("comments", "New Gas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ne")))
                .andExpect(content().string(containsString("https://m.media-amazon.com/images/I/71H0PYikiRL._AC_SL1500_.jpg")));
    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }

}
