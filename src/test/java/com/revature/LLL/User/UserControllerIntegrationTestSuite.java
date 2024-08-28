package com.revature.LLL.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Integration tests for the UserController class.
 *
 * These tests cover post, get and put requests
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    HttpHeaders headers;
    User mockUser;

    /**
     * Sets up the testing environment by initializing headers and mock user data.
     *
     * Whenever findById is called with input 1, return mockUser for future tests.
     */
    @BeforeEach
    public void setup(){
        headers = new HttpHeaders();
        headers.add("userId", "");

        mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("test2@email.com");
        mockUser.setPassword("password");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
    }

    /**
     * Tests the registration of a new user with valid input.
     * expects user with valid input to return a status 201
     *
     * @throws Exception if request fails
     */
    @Test
    public void whenPostRequestValidUserThenCorrectResponse() throws Exception {
        // user successfully registers with all their info
        String validUser =
                "{\n" +
                        "\"firstName\": \"test\",\n" +
                        "\"lastName\": \"user\",\n" +
                        "\"email\": \"test1@email.com\",\n" +
                        "\"password\": \"password\",\n" +
                        "\"userType\": \"VET\"\n" +
                "}";

        User tempUser = new User();
        tempUser.setFirstName("test");
        tempUser.setLastName("user");
        tempUser.setEmail("test1@email.com");
        tempUser.setPassword("password");

        User newUser = new User();
        newUser.setUserId(1);
        newUser.setFirstName("test");
        newUser.setLastName("user");
        newUser.setEmail("test1@email.com");
        newUser.setPassword("password");
        Mockito.when(userRepository.saveAndFlush(tempUser)).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validUser))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    /**
     * Tests the registration of a user when no email is provided.
     * Test expects it to return status 400 checking authorization is working
     * when given no email.
     *
     * @throws Exception if request fails
     */
    @Test
    public void whenPostRequestNoEmailThenError() throws Exception {
        // user neglects to input an email.

        String invalidUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"test2@email.com\",\n" +
                        "\"password\": \"password\"\n" +
                        "\"userType\": \"VET\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(invalidUser))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Tests the registration of a user when no password is provided.
     * Test expects it to return status 400 checking authorization is working
     * when given no password.
     *
     * @throws Exception if request fails
     */
    @Test
    public void whenPostRequestNoPasswordThenError() throws Exception{
        // user neglects to input a password

        String invalidUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"test2@email.com\",\n" +
                        "\"userType\": \"VET\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(invalidUser))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Tests a successful GET request to retrieve user data.
     * Test checks to see if status 200 is received  to view their profile.
     *
     * @throws Exception if request  fails
     */
    @Test
    public void whenGetRequestValidThenCorrectResponse() throws Exception{
        // user 1 trying to view their own profile
        headers.set("userId", "1");

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
    }

    /**
     * Tests an unauthorized GET request to retrieve a users profile.
     * Test checks to see if status 403 is received ensuring invalid input returns a failed request
     *
     * @throws Exception if request fails
     */
    @Test
    public void whenGetRequestInvalidThenIncorrectResponse() throws Exception {
        // user 2 trying to view user 1's profile
        headers.set("userId", "2");

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .headers(headers))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }


    /**
     * Tests a successful PUT request to update a user's info.
     * Test checks to see of status 200 is received when updating
     *
     * @throws Exception if request fails
     */
    @Test
    public void whenPutRequestValidThenUpdateInfo() throws Exception {
        // user updates their own info
        headers.set("userId", "1");

        String updatedInfo =
                "{\n" +
                        "\"firstName\": \"Jane\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"test2@email.com\",\n" +
                        "\"password\": \"password\",\n" +
                        "\"userType\": \"VET\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updatedInfo))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testPutUpdateUser() throws Exception{
        String validUser =
                "{\n" +
                        "\"firstName\": \"John\",\n" +
                        "\"lastName\": \"Doe\",\n" +
                        "\"email\": \"john.doe@example.com\",\n" +
                        "\"password\": \"password3\"\n" +
                        "}";


        doNothing().when(userService).update((any(User.class)));

        mockMvc.perform(put("updatePassword"));



    }
}
