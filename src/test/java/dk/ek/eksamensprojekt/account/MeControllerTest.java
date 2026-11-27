package dk.ek.eksamensprojekt.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeController.class)
class MeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "jens", roles = {"ADMIN", "INSTRUCTOR"})
    void returnsUsernameAndRolesForLoggedInUser() throws Exception {
        mockMvc.perform(get("/api/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jens"))
                .andExpect(jsonPath("$.roles",
                        containsInAnyOrder("ROLE_ADMIN", "ROLE_INSTRUCTOR")));
    }

    @Test
    void returnsUnauthorizedWithoutLogin() throws Exception {
        mockMvc.perform(get("/api/me"))
                .andExpect(status().isUnauthorized());
    }

}
