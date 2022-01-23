package uk.ac.cardiff.mma.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class user_management_test {

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(roles="ADMIN")
    public void whenGetUserListViewThenGetUserListView() {
        try {
            this.mvc.perform(get("/superadmin/user_list"))
                    .andDo(print())
                    .andExpect(content().string(containsString("add user")));
        } catch(Exception exception) {

            //test
        }
    }

}
