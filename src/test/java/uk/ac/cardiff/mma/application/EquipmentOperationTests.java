package uk.ac.cardiff.mma.application;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class EquipmentOperationTests {
    @Autowired
    private MockMvc mockMvc;
    private static JSONObject info;
    private static JSONObject infoAdd;
    private static JSONObject infoEdit;
    private static JSONObject infoDelete;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
        info= new JSONObject();
        info.put("name","test");
        info.put("mode","0");
        info.put("value","1");
        infoEdit= new JSONObject();
        infoDelete=new JSONObject();
        infoAdd =new JSONObject();
        infoAdd.put("name","testTEST");
        infoAdd.put("mode","0");
        infoAdd.put("value","1");

    }



    @Test
    @WithMockUser(roles="ADMIN")
    public void equipmentOperationPageTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/equipmentOperation")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andExpect(content().string(containsString("EquipmentOperationPage")));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void equipmentOperationReportPageTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/equipmentOperationReport")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andExpect(content().string(containsString("Print Check Result")));
    }


    @WithMockUser(roles="USER")
    public void checkItemAdd() throws Exception {

        String requestJson = JSONObject.toJSONString(info);
        this.mockMvc.perform(post("/checkItemAdd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void checkItemAddTest() throws Exception {

        String requestJson = JSONObject.toJSONString(infoAdd);
        this.mockMvc.perform(post("/checkItemAdd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("TEST")));
    }

    @Test
    @WithMockUser(roles="USER")
    public  void checkItemDeleteTest() throws Exception {
        this.checkItemAdd();
        String res=this.mockMvc.perform(get("/checkItemAll")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        int id= (int) JSONArray.parseArray(res).getJSONObject(JSONArray.parseArray(res).size()-1).get("id");
        String name=(String) JSONArray.parseArray(res).getJSONObject(JSONArray.parseArray(res).size()-1).get("name");


        infoDelete.put("id",id);
        infoDelete.put("name",name);

        String requestJson = JSONObject.toJSONString(infoDelete);
        this.mockMvc.perform(post("/checkItemDelete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk());

    }



    @Test
    @WithMockUser(roles="USER")
    public  void checkItemEditTest() throws Exception {
        this.checkItemAdd();
        String res=this.mockMvc.perform(get("/checkItemAll")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        int id= (int) JSONArray.parseArray(res).getJSONObject(JSONArray.parseArray(res).size()-1).get("id");

        infoEdit.put("id",id);
        infoEdit.put("name","test1");
        infoEdit.put("mode","0");
        infoEdit.put("value","1");
        String requestJson = JSONObject.toJSONString(infoEdit);
        this.mockMvc.perform(post("/checkItemEdit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test1")));

    }



    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }

}
