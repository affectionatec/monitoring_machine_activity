package uk.ac.cardiff.mma.application;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class InvoiceControllerTests {
    @Autowired
    MockMvc mvc;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
    }

    // MOCKMVC INTEGRATION TESTS WITH ENDPOINT SECURITY
    @Test
    @WithMockUser(roles="ADMIN")
    public void whenGetInvoiceGeneratorThenDisplayInvoiceGeneratorView() throws Exception {
        this.mvc.perform(get("/admin/invoiceGenerator"))
                .andDo(print())
                .andExpect(content().string(containsString("<option value=\"TEST_USER\">TEST_USER</option>")))
                .andExpect(content().string(containsString("<option value=\"2222\">2222</option>")));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void whenGenerateInvoiceThenDisplayGeneratedInvoice() throws Exception {
        this.mvc.perform(post("/admin/generateInvoice")
                .contentType("application/x-www-form-urlencoded")
                .param("username", "TEST_USER")
                .param("month", "03")
                .param("year", "2222"))
                .andDo(print())
                .andExpect(content().string(containsString("<td>ICP1</td>")))
                .andExpect(content().string(containsString("<td>2</td>")))      // Time Slots Used
                .andExpect(content().string(containsString("<td>11.0</td>")))    // Charge Rate per Time Slot (FLOAT)
                .andExpect(content().string(containsString("<td>22.0</td>")));   // Total Charge (FLOAT)
    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }
}
