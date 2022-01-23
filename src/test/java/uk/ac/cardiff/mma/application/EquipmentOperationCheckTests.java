package uk.ac.cardiff.mma.application;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class EquipmentOperationCheckTests {

    @Autowired
    private MockMvc mockMvc;
    private static JSONObject info;
    private static JSONObject checkData;
    private static ArrayList lis;


    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
        info= new JSONObject();
        info.put("name","testDemo");
        info.put("mode","0");
        info.put("value","1");
        lis=new ArrayList<>();
        Map map=new JSONObject();
        map.put("testDemo","1");
        lis.add(map);
        checkData= new JSONObject();


    }

    @WithMockUser(roles="USER")
    public void checkItemAdd() throws Exception {

        String requestJson = JSONObject.toJSONString(info);
        this.mockMvc.perform(post("/checkItemAdd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("testDemo")));
    }


    @Test
    @WithMockUser(roles="USER")
    public  void checkPostOperateDataTest() throws Exception {
        this.checkItemAdd();

        checkData.put("date","2021-09-20");
        checkData.put("name","TEST_EQUIPMENT");
        checkData.put("operateData",lis);
        System.out.println(checkData);
        String requestJson = JSONObject.toJSONString(checkData);
        this.mockMvc.perform(post("/postOperateData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"date\":\"2021-09-20\",\"equipmentName\":\"TEST_EQUIPMENT\",\"testDemo\":\"1\"}")))
        .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.standardList").isNotEmpty())
                .andExpect(jsonPath("$.dataList").isNotEmpty())
                .andExpect(jsonPath("$.dateList").isNotEmpty());

    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }


}
