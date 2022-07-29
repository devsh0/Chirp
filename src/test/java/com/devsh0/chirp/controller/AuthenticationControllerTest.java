package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequest;
import com.devsh0.chirp.dto.RegistrationResponse;
import com.devsh0.chirp.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private MockHttpServletResponse registrationRequestHelper(RegistrationRequest requestStub) throws Exception {
        return this.mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.asJson(requestStub)))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse tokenVerificationRequestHelper(String token) throws Exception {
        return this.mockMvc.perform(get("/api/v1/auth/verify")
                .param("token", token)).andReturn().getResponse();
    }

    @Test
    public void registrationSucceedsOnValidInput() throws Exception {
        var request = new RegistrationRequest("test@test.com", "user", "password");
        try {
            var response = registrationRequestHelper(request);
            var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
            var expectedResponseBody = new RegistrationResponse(true, Utils.emptyMap());
            assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
            assertThat(responseBody).isEqualTo(expectedResponseBody);
        } catch (NestedServletException exc) {
            // We have temporarily changed smtp settings to prevent mail service from sending real mails.
            assertThat(exc.getCause() instanceof MailSendException).isTrue();
        }
    }

    @Test
    public void registrationFailsOnInvalidEmail() throws Exception {
        var request = new RegistrationRequest("test", "user", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var expectedResponseBody = new RegistrationResponse(false, Utils.mapFrom("email", "invalid email!").get());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidUsername() throws Exception {
        var request = new RegistrationRequest("test@mail.com", "us", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("username", "username must be between 3 to 30 characters long!").get();
        var expectedResponseBody = new RegistrationResponse(false, errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidPassword() throws Exception {
        var request = new RegistrationRequest("test@mail.com", "user", "pass");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("password", "password must be at least 8 characters long!").get();
        var expectedResponseBody = new RegistrationResponse(false, errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }
}
