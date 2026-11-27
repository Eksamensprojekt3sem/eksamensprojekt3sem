package dk.ek.eksamensprojekt.account;

import dk.ek.eksamensprojekt.common.ConflictException;
import dk.ek.eksamensprojekt.common.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@WithMockUser
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AccountService accountService;

    @Test
    void register_newMember_succesCreated() throws Exception {
        mockMvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "anna@example.com", "password": "kodeord123", "role": "MEMBER"}
                """)).andExpect(status().isCreated());

    }

    @Test
    void register_invalidEmail_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "ANNABANNANA", "password": "kodeord123", "role": "MEMBER"}
                """)).andExpect(status().isBadRequest());
    }

    @Test
    void register_shortPassword_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "anna@example.com", "password": "kode", "role": "MEMBER"}
                """)).andExpect(status().isBadRequest());
    }

    @Test
    void register_existingEmail_returnsConflict() throws Exception {
        doThrow(new ConflictException("Emailen findes allerede")).when(accountService).register(any());
        mockMvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "anna@example.com", "password": "kodeord123", "role": "MEMBER"}
                """)).andExpect(status().isConflict());
    }

    @Test
    void register_invalidRole_returnsBadRequest() throws Exception {
        doThrow(new InvalidInputException("Rollen er ugyldig")).when(accountService).register(any());
        mockMvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "anna@example.com", "password": "kodeord123", "role": "ADMIN"}
                """)).andExpect(status().isBadRequest());
    }

    @Test
    void register_missingCsrf_returnsForbidden() throws Exception {
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content("""
                {"email": "anna@example.com", "password": "kodeord123", "role": "MEMBER"}
                """)).andExpect(status().isForbidden());

    }
}
