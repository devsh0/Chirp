package com.devsh0.chirp.controller;

import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.mock.web.MockHttpServletResponse.SC_BAD_REQUEST;
import static org.springframework.mock.web.MockHttpServletResponse.SC_OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {
    private static final String email = "test@test.com";
    private static final String username = "user";
    private static final String password = "password";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authService;

    @Test
    public void registrationSucceedsOnValidInput() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", email).put("username", username).put("password", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }

    @Test
    public void registrationFailsOnMissingEmail() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("username", username).put("password", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void registrationFailsOnInvalidEmail() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", "blah").put("username", username).put("password", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void registrationFailsOnMissingUsername() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", email).put("password", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void registrationFailsOnInvalidUsername() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", email).put("username", "ab").put("password", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void registrationFailsOnMissingPassword() throws Exception {
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", email).put("username", username).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void registrationFailsOnInvalidPassword() throws Exception {
        var request = new RegistrationRequest(email, username, "pass");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("password", "password must be at least 8 characters long!").get();
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }
}
