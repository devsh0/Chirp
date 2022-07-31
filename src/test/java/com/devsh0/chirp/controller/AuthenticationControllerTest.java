package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.request.RegistrationRequest;
import com.devsh0.chirp.dto.response.RegistrationResponse;
import com.devsh0.chirp.entity.User;
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
import static org.mockito.Mockito.when;
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

    private MockHttpServletResponse registrationRequestHelper(RegistrationRequest requestStub) throws Exception {
        return this.mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJson(requestStub)))
                .andReturn().getResponse();
    }

    @Test
    public void registrationSucceedsOnValidInput() throws Exception {
        var validUser = User.builder().email(email).username(username).password(password).build();
        when(authService.register(email, username, password)).thenReturn(validUser);
        var request = new RegistrationRequest(email, username, password);
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var expectedResponseBody = new RegistrationResponse().setSuccess(true).setError(Utils.emptyMap());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(responseBody).isEqualTo(expectedResponseBody);

    }

    @Test
    public void registrationFailsOnInvalidEmail() throws Exception {
        var request = new RegistrationRequest("test", username, password);
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(Utils.mapFrom("email", "invalid email!").get());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidUsername() throws Exception {
        var request = new RegistrationRequest(email, "us", password);
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("username", "username must be between 3 to 30 characters long!").get();
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
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
