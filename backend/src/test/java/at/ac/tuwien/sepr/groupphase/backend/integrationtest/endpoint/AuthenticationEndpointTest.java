package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
class AuthenticationEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String AUTHENTICATION_BASE = "/api/v1/authentication";

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void loginTestAdmin_CorrectCredentials() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "admin@email.com");
        requestBody.put("password", "password");

        MvcResult mvcResult = this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType()),
            () -> assertThat(response.getContentAsString()).startsWith("Bearer ")
        );
    }

    @Test
    void loginTestAdmin_InvalidCredentials() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "admin@email.com");
        requestBody.put("password", "invalid");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Username or password is incorrect")
        );
    }

    @Test
    void loginTestNormalUser_CorrectCredentials() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "user1@email.com");
        requestBody.put("password", "password");

        MvcResult mvcResult = this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType()),
            () -> assertThat(response.getContentAsString()).startsWith("Bearer ")
        );
    }

    @Test
    void loginTestNormalUser_InvalidCredentials() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "user1@email.com");
        requestBody.put("password", "invalid");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Username or password is incorrect")
        );
    }

    @Test
    void loginTestNormalUser_NonExistentUser() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "xxxxxx@xx.com");
        requestBody.put("password", "password");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isNotFound(),
            jsonPath("$.status").value("404"),
            jsonPath("$.error").value("No user with email xxxxxx@xx.com found")
        );
    }

    @Test
    void lockedAccountByAdminCanNotLogin() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "locked@email.com");
        requestBody.put("password", "password");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Account is locked")
        );
    }

    @Test
    @DirtiesContext
    void login_asNormalUser_locksAccountAfter5Attempts_invalidLogin() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "user2@email.com");
        requestBody.put("password", "invalid");

        ApplicationUser user = applicationUserRepository.findUserByEmail("user2@email.com").orElseThrow();
        user.setFailedAuths(4);
        applicationUserRepository.save(user);

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Incorrectly entered password too many times. Account is now locked.")
        );
    }

    @Test
    @DirtiesContext
    void login_asNormalUser_locksAccountAfter5Attempts_validLogin() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "user2@email.com");
        requestBody.put("password", "invalid");


        ApplicationUser user = applicationUserRepository.findUserByEmail("user2@email.com").orElseThrow();
        user.setFailedAuths(4);
        applicationUserRepository.save(user);

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Incorrectly entered password too many times. Account is now locked.")
        );

        requestBody.put("password", "password");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isForbidden(),
            jsonPath("$.status").value("403"),
            jsonPath("$.error").value("Account is locked")
        );
    }

    @Test
    @DirtiesContext
    void registerNewAccount() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "newAccount@email.com");
        requestBody.put("password", "password");
        requestBody.put("confirmPassword", "password");
        requestBody.put("firstName", "Toller");
        requestBody.put("lastName", "Name");

        var location = new HashMap<String, String>();
        location.put("address", "Hansenstraße");
        location.put("postalCode", "1010");
        location.put("city", "Vienna");
        location.put("country", "Austria");

        requestBody.put("location", location);

        MvcResult mvcResult = this.mockMvc.perform(post(AUTHENTICATION_BASE + "/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType()),
            () -> assertThat(response.getContentAsString()).startsWith("Bearer ")
        );

        var loginRequestBody = new HashMap<String, Object>();
        loginRequestBody.put("email", "newAccount@email.com");
        loginRequestBody.put("password", "password");

        MvcResult loginMvcResult = this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        MockHttpServletResponse loginResponse = loginMvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), loginResponse.getStatus()),
            () -> assertEquals(MediaType.APPLICATION_JSON_VALUE, loginResponse.getContentType()),
            () -> assertThat(loginResponse.getContentAsString()).startsWith("Bearer ")
        );
    }

    @Test
    @DirtiesContext
    void registeringNewAccountWithoutAddressReturnsError() throws Exception {
        var requestBody = new HashMap<String, Object>();
        requestBody.put("email", "withoutaddress@email.com");
        requestBody.put("password", "password");
        requestBody.put("confirmPassword", "password");
        requestBody.put("firstName", "without");
        requestBody.put("lastName", "address");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.timestamp").isNotEmpty())
            .andExpect(jsonPath("$.error").value("Invalid request content."))
            .andExpect(jsonPath("$.subErrors.location").value("must not be null"));


        var loginRequestBody = new HashMap<String, Object>();
        loginRequestBody.put("email", "withoutaddress@email.com");
        loginRequestBody.put("password", "password");

        this.mockMvc.perform(post(AUTHENTICATION_BASE + "/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isNotFound(),
            jsonPath("$.status").value("404"),
            jsonPath("$.error").value("No user with email withoutaddress@email.com found")
        );
    }


}
