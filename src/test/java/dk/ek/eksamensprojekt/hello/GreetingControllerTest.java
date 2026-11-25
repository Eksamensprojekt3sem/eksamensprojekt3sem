package dk.ek.eksamensprojekt.hello;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class GreetingControllerTest {

    private GreetingService greetingService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        greetingService = mock(GreetingService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new GreetingController(greetingService)).build();
    }

    @Test
    void getAllReturns200WithGreetings() throws Exception {
        when(greetingService.getAll()).thenReturn(List.of(new GreetingResponse(1L, "Hej")));

        mockMvc.perform(get("/api/greetings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("Hej"));
    }
}