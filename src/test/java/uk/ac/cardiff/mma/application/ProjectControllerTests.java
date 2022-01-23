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
import uk.ac.cardiff.mma.application.repository.ProjectRepositoryJDBC;

import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = SecurityTestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application-test.properties")
public class ProjectControllerTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    ProjectRepositoryJDBC projectRepositoryJDBC;

    private static String projectInfoValid;
    private static String projectInfoInvalid;

    @BeforeAll
    public static void before() {
        projectInfoValid = "{\"code\": \"VALID_TEST_CODE\", \"name\": \"TEST_PROJECT\"}";
        projectInfoInvalid = "{\"code\": \"INVALID_TEST_CODE_MORE_THAN_20_CHARACTERS\", \"name\": \"TEST_PROJECT\"}";
    }

    // MOCKMVC INTEGRATION TESTS WITH ENDPOINT SECURITY
    @Test
    @WithMockUser(roles="SUPERADMIN")
    public void whenCreateValidProjectCodeThenReturnCREATED() throws Exception {
        this.mvc.perform(post("/superadmin/createProject")
                .contentType("application/json")
                .content(projectInfoValid))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    // NEGATIVE CASE TESTING --> Violation of VARCHAR(20) upper character limit should return BADREQUEST
    @Test
    @WithMockUser(roles="SUPERADMIN")
    public void whenCreateInvalidProjectCodeThenReturnBADREQUEST() throws Exception {
        this.mvc.perform(post("/superadmin/createProject")
                .contentType("application/json")
                .content(projectInfoInvalid))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @AfterAll
    public static void after() throws IOException, SQLException {
        TestUtils.destroyTestRecords();
    }
}
