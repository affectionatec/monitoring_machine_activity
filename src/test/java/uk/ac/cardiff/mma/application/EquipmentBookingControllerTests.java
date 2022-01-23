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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.StringContains.containsString;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class EquipmentBookingControllerTests {
    @Autowired
    MockMvc mvc;

    private static String equipmentIDAndDateJSON;
    private static String bookingInfoJSON;
    private static String bookingUsageInfoJSON;
    private static String bookingProjectCodeInfoJSON;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();

        equipmentIDAndDateJSON = "{\"equipmentID\":1,\"date\":\"02/02/2222\"}";
        bookingInfoJSON = "{\"equipmentID\":1,\"username\":\"TEST_USER\",\"date\":\"07/06/2222\",\"times\":[\"t1\",\"t2\"],\"projectCode\":\"\"}";
        bookingUsageInfoJSON = "{\"equipmentName\":\"TEST_EQUIPMENT\",\"username\":\"TEST_USER\",\"date\":\"2222-02-02\",\"timeSlot\":\"8:00-8:30\",\"used\":1}";
        bookingProjectCodeInfoJSON = "{\"equipmentName\":\"TEST_EQUIPMENT\",\"username\":\"TEST_USER\",\"date\":\"2222-02-02\",\"timeSlot\":\"8:00-8:30\",\"projectCode\":\"TEST_CODE_123\"}";
    }

    // MOCKMVC INTEGRATION TESTS WITH ENDPOINT SECURITY
    @Test
    @WithMockUser(roles="USER")
    public void whenGetEquipmentBookingViewThenDisplayEquipmentBookingView() throws Exception {
        this.mvc.perform(get("/equipmentBooking"))
                .andDo(print())
                .andExpect(content().string(containsString("Book Now")));
    }

    // Given equipmentID, check fully booked dates are returned from repository layer
    @Test
    @WithMockUser(roles="USER")
    public void whenGetEquipmentBookingDateTimeViewThenDisplayEquipmentBookingDateTimeView() throws Exception {
        this.mvc.perform(post("/equipmentBooking/datetime")
                .contentType("application/x-www-form-urlencoded")
                .param("equipmentID", "e1"))
                .andDo(print())
                .andExpect(content().string(containsString("Choose a date")))
                .andExpect(content().string(containsString("Choose a time slot")))
                .andExpect(content().string(containsString("let dates = [\"2222-02-02\",\"2222-01-01\"]")))   // unavailable dates array
                .andExpect(content().string(containsString("Choose a project code:")));
    }

    // Given equipmentID and date, check booked times returned from repository layer
    @Test
    @WithMockUser(roles="USER")
    public void whenGetUnavailableTimesThenReturnUnavailableTimes() throws Exception {
        this.mvc.perform(post("/getUnavailableTimes")
                .contentType("application/json")
                .content(equipmentIDAndDateJSON))
                .andExpect(content().json("[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]")); // parse JSON response content and expect specified JSON
    }

    @Test
    @WithMockUser(roles="USER")
    public void whenMakeBookingThenReturnCREATED() throws Exception {
        this.mvc.perform(post("/makeBooking")
                .contentType("application/json")
                .content(bookingInfoJSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="USER")
    public void whenUpdateUsageThenReturnCREATED() throws Exception {
        this.mvc.perform(post("/updateUsage")
                .contentType("application/json")
                .content(bookingUsageInfoJSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="USER")
    public void whenUpdateProjectCodeThenReturnCREATED() throws Exception {
        this.mvc.perform(post("/updateProjectCode")
                .contentType("application/json")
                .content(bookingProjectCodeInfoJSON))
                .andExpect(status().isCreated());
    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }
}
