package org.andela.ryder.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testRegisterCustomer() throws Exception {
        // Create a RegisterRequest object to send in the request
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("testUser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("testUser");
        registerRequest.setConfirmedPassword("testUser");

        // Convert the RegisterRequest to JSON
        String requestJson = objectMapper.writeValueAsString(registerRequest);

        // Perform a POST request to the /api/v1/customers/register endpoint
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        );

        // Verify the response
        result
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
        // Add more assertions as needed
    }

    @Test
    public void testGetCustomer() throws Exception {
        // Replace customerId with a valid customer ID
        Long customerId = 1L;

        // Perform a GET request to the /api/v1/customers/one/{customerId} endpoint
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/customers/one/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Verify the response
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId));
        // Add more assertions as needed
    }
}
