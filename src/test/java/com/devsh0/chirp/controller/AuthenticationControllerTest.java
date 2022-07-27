package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;
import com.devsh0.chirp.util.Utils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private MockHttpServletResponse registrationRequestHelper(RegistrationRequestBody requestStub) throws Exception {
        return this.mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJson(requestStub)))
                .andReturn().getResponse();
    }

    @Disabled
    @Test
    public void registrationSucceedsOnValidInput() throws Exception {
        var request = new RegistrationRequestBody("test@test.com", "user", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponseBody.class);
        var expectedResponseBody = new RegistrationResponseBody(true, Utils.emptyMap());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidEmail() throws Exception {
        var request = new RegistrationRequestBody("test", "user", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponseBody.class);
        var expectedResponseBody = new RegistrationResponseBody(false, Utils.mapOf("email", "invalid email!").get());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidUsername() throws Exception {
        var request = new RegistrationRequestBody("test@mail.com", "us", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponseBody.class);
        var errorMap = Utils.mapOf("username", "username must be between 3 to 30 characters long!").get();
        var expectedResponseBody = new RegistrationResponseBody(false, errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidPassword() throws Exception {
        var request = new RegistrationRequestBody("test@mail.com", "user", "pass");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponseBody.class);
        var errorMap = Utils.mapOf("password", "password must be at least 8 characters long!").get();
        var expectedResponseBody = new RegistrationResponseBody(false, errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }
}
