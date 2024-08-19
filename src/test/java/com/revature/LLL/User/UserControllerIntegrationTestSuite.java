package com.revature.LLL.User;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {
    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestValidUserThenCorrectResponse() throws Exception {
        String validUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"test1@email.com\",\n" +
                        "\"password\": \"password\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validUser))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }
}
