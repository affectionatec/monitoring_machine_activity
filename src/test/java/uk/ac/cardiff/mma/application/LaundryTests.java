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
public class LaundryTests {

    @Autowired
    private MockMvc mockMvc;
    private static JSONObject laundry;
    private static JSONObject suit;
    private static JSONObject suitAdd;
    private static JSONObject suitEdit;
    private static JSONObject barcode;

    @BeforeAll
    public static void before() throws IOException, SQLException {
        TestUtils.createTestRecords();
        laundry=new JSONObject();
        laundry.put("barcode","9999");
        laundry.put("status","TestClean");
        laundry.put("comment","TestClean");

        suit=new JSONObject();
        suit.put("barcode","9999");
        suit.put("status","in use");
        suit.put("size","S");
        suit.put("item","suit");
        suitAdd=new JSONObject();
        suitAdd.put("barcode","9998");
        suitAdd.put("status","in use");
        suitAdd.put("size","TestADD");
        suitAdd.put("item","suit");

        suitEdit=new JSONObject();
        suitEdit.put("barcode","9998");
        suitEdit.put("status","in use");
        suitEdit.put("size","TestEdit");
        suitEdit.put("item","suit");

    }

    //  Unit tests using a mockMVC - no change here from container testing as no access to database
    @Test
    @WithMockUser(roles="ADMIN")
    public void laundryPageTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/laundryInfo")
                .accept(MediaType.APPLICATION_JSON_UTF8) // accept response content type
        ).andExpect(status().isOk()).andDo(print()).andExpect(content().string(containsString("LaundryListPage")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void laundryInfoTest() throws Exception {
        String requestJson = JSONObject.toJSONString(laundry);
        this.mockMvc.perform(post("/update/laundryInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("TestClean")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void itemAddTest() throws Exception {

        String requestJson = JSONObject.toJSONString(suit);
        this.mockMvc.perform(post("/add/laundryItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("in use")));
    }


    @WithMockUser(roles="USER")
    public void itemAdd() throws Exception {
        String requestJson = JSONObject.toJSONString(suitAdd);
        this.mockMvc.perform(post("/add/laundryItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("TestADD")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void itemEditTest() throws Exception {
        this.itemAdd();
        barcode=new JSONObject();
        barcode.put("barcode","9998");
        String barcodeJson = JSONObject.toJSONString(barcode);
        String res=this.mockMvc.perform(post("/getLaundryInfoByCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(barcodeJson)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);

        int id= (int) JSONArray.parseArray(res).getJSONObject(JSONArray.parseArray(res).size()-1).get("id");
        suitEdit.put("id",id);

        String requestJson = JSONObject.toJSONString(suitEdit);
        this.mockMvc.perform(post("/update/laundryItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("TestEdit")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void itemDeleteTest() throws Exception {

        String requestJson = JSONObject.toJSONString(suitEdit);
        this.mockMvc.perform(post("/delete/laundryItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andDo(print()).andExpect(status().isOk());
    }





    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }

}
