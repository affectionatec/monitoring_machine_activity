package uk.ac.cardiff.mma.application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.ac.cardiff.mma.application.repository.EquipmentUsageRepository;


import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class EquipmentUsageTests {


    @Autowired
    private MockMvc mockMvc;
    private static JSONObject equipmentName;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
        equipmentName=new JSONObject();
        equipmentName.put("name","TEST_EQUIPMENT");
    }

    //  Unit tests using a mockMVC - no change here from container testing as no access to database
    @Test
    @WithMockUser(roles="ADMIN")
    public void equipmentUsagePageTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/equipmentUsageList")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andExpect(content().string(containsString("EquipmentUsagePage")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void equipmentUsagePageGraphTest() throws Exception {
        String requestJson = JSONObject.toJSONString(equipmentName);
        this.mockMvc.perform(post("/postGraphData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk());
    }



    @Test
    @WithMockUser(roles="USER")
    public void equipmentListTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/getEquipmentList")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("TEST_EQUIPMENT")));
    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }
}
