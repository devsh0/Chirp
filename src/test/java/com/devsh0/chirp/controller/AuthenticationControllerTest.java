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
        String path = "/api/v1/auth/register";
        var request = Utils.mapFrom("email", email).put("username", username).put("password", "blah").asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(request)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void tokenVerificationFailsOnMissingTokenParam() throws Exception {
        String path = "/api/v1/auth/verify";
        var response = mockMvc.perform(get(path)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void tokenVerificationSucceedsOnValidTokenParam() throws Exception {
        String path = "/api/v1/auth/verify";
        var response = mockMvc.perform(get(path).param("token", "")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
    }

    @Test
    public void loginFailsOnMissingUserField() throws Exception {
        String path = "/api/v1/auth/login";
        var requestBody = Utils.mapFrom("password", "pass").asJson();
        var response = mockMvc.perform(post(path).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void loginFailsOnMissingPasswordField() throws Exception {
        String path = "/api/v1/auth/login";
        var requestBody = Utils.mapFrom("user", "user").asJson();
        var response = mockMvc.perform(post(path).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void loginFailsOnEmptyUser() throws Exception {
        String path = "/api/v1/auth/login";
        var requestBody = Utils.mapFrom("user", "").put("password", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void loginFailsOnEmptyPassword() throws Exception {
        String path = "/api/v1/auth/login";
        var requestBody = Utils.mapFrom("user", username).put("password", "").asJson();
        var response = mockMvc.perform(post(path).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void loginSucceedsOnValidInput() throws Exception {
        String path = "/api/v1/auth/login";
        var requestBody = Utils.mapFrom("user", username).put("password", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }

    @Test
    public void passwordResetFailsOnMissingAuthorizationHeader() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("oldPassword", password).put("newPassword", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void passwordResetFailsOnMissingOldPassword() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("newPassword", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void passwordResetFailsOnMissingNewPassword() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("oldPassword", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void passwordResetFailsOnEmptyOldPassword() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("oldPassword", "").put("newPassword", password).asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void passwordResetFailsOnEmptyNewPassword() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("oldPassword", password).put("newPassword", "").asJson();
        var response = mockMvc.perform(post(path).content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    public void passwordResetSucceedsOnValidInput() throws Exception {
        String path = "/api/v1/auth/reset-password";
        var requestBody = Utils.mapFrom("newPassword", password).put("oldPassword", password).asJson();
        var response = mockMvc.perform(post(path).header("Authorization", "")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }

    @Test
    public void logoutFailsOnMissingAuthorizationHeader() throws Exception {
        String path = "/api/v1/auth/logout";
        var response = mockMvc.perform(post(path)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void logoutSucceedsWhenAuthorizationHeaderIsPresent() throws Exception {
        String path = "/api/v1/auth/logout";
        var response = mockMvc.perform(post(path).header("Authorization", "")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }

    @Test
    void passwordRecoveryFailsOnMissingEmail() throws Exception {
        String path = "/api/v1/auth/recover-password";
        var response = mockMvc.perform(get(path)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void passwordRecoveryFailsOnInvalidEmail() throws Exception {
        String path = "/api/v1/auth/recover-password";
        var requestBody = Utils.mapFrom("email", "invalid#email.com").asJson();
        var response = mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void passwordRecoverySucceedsOnValidEmail() throws Exception {
        String path = "/api/v1/auth/recover-password";
        var requestBody = Utils.mapFrom("email", email).asJson();
        var response = mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }

    @Test
    void createNewPasswordFailsOnMissingPassword() throws Exception {
        String path = "/api/v1/auth/create-new-password";
        var requestBody = Utils.mapFrom("token", "abc").asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void createNewPasswordFailsOnMissingToken() throws Exception {
        String path = "/api/v1/auth/create-new-password";
        var requestBody = Utils.mapFrom("newPassword", password).asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void createNewPasswordFailsOnInvalidPassword() throws Exception {
        String path = "/api/v1/auth/create-new-password";
        var requestBody = Utils.mapFrom("newPassword", "sevenn7").put("token", "").asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_BAD_REQUEST);
    }

    @Test
    void createNewPasswordSucceedsOnValidInput() throws Exception {
        String path = "/api/v1/auth/create-new-password";
        var requestBody = Utils.mapFrom("newPassword", password).put("token", "").asJson();
        var response = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(SC_OK);
    }
}
