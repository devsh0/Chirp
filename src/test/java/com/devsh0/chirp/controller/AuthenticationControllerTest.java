package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequest;
import com.devsh0.chirp.dto.RegistrationResponse;
import com.devsh0.chirp.dto.VerificationResponse;
import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.repository.VerificationTokenRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

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

    private User createActivatedUser() {
        var user = User.builder().build();
        user.
    }

    @Test
    public void registrationSucceedsOnValidInput() throws Exception {
        var request = new RegistrationRequest("test@test.com", "user", "password");
        try {
            var response = registrationRequestHelper(request);
            var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
            var expectedResponseBody = new RegistrationResponse().setSuccess(true).setError(Utils.emptyMap());
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
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(Utils.mapFrom("email", "invalid email!").get());
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidUsername() throws Exception {
        var request = new RegistrationRequest("test@mail.com", "us", "password");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("username", "username must be between 3 to 30 characters long!").get();
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void registrationFailsOnInvalidPassword() throws Exception {
        var request = new RegistrationRequest("test@mail.com", "user", "pass");
        var response = registrationRequestHelper(request);
        var responseBody = Utils.fromJson(response.getContentAsString(), RegistrationResponse.class);
        var errorMap = Utils.mapFrom("password", "password must be at least 8 characters long!").get();
        var expectedResponseBody = new RegistrationResponse().setSuccess(false).setError(errorMap);
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_BAD_REQUEST);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void tokenVerificationFailsOnNonExistentToken() throws Exception {
        var response = tokenVerificationRequestHelper("non-existent-token");
        var responseBody = Utils.fromJson(response.getContentAsString(), VerificationResponse.class);
        var expectedResponseBody = VerificationResponse.failure("token does not exist!");
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void tokenVerificationFailsOnExpiredTokens() throws Exception {
        // Create a demo user.
        var demoUser = User.builder().email("test@test.com").username("test").password("password").build();
        demoUser = userRepository.save(demoUser);

        // Create an expired token.
        VerificationToken expiredToken = VerificationToken.generateToken(demoUser);
        expiredToken.setExpiry(Date.valueOf("2020-01-01"));
        tokenRepository.save(expiredToken);
        assertThat(tokenRepository.findByToken(expiredToken.getToken())).isNotNull();

        // Confirm that the token verification fails.
        var response = tokenVerificationRequestHelper(expiredToken.getToken());
        var responseBody = Utils.fromJson(response.getContentAsString(), VerificationResponse.class);
        var expectedResponseBody = VerificationResponse.failure("token expired!");
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_UNAUTHORIZED);
        assertThat(responseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void tokenVerificationSucceedsAndAccountIsActivated() throws Exception {
        // Create a demo user.
        var demoUser = User.builder().email("test@test.com").username("test").password("password").build();
        demoUser = userRepository.save(demoUser);
        assertThat(demoUser.isActive()).isFalse();

        // Create a valid token.
        VerificationToken validToken = VerificationToken.generateToken(demoUser);
        tokenRepository.save(validToken);

        // Confirm that the token verification succeeds.
        var response = tokenVerificationRequestHelper(validToken.getToken());
        var responseBody = Utils.fromJson(response.getContentAsString(), VerificationResponse.class);
        var expectedResponseBody = VerificationResponse.success();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(responseBody).isEqualTo(expectedResponseBody);

        // Confirm that the account is activated.
        demoUser = userRepository.getReferenceById(demoUser.getId());
        assertThat(demoUser.isActive()).isTrue();
    }
}
