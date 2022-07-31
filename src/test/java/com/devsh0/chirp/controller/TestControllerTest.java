package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.response.BasicResponse;
import com.devsh0.chirp.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        var response = mockMvc.perform(get("/api/v1/test")).andReturn().getResponse();
        assertEquals(response.getStatus(), MockHttpServletResponse.SC_OK);
        var expectedResponseBody = Utils.fromJson(response.getContentAsString(), BasicResponse.class);
        assertEquals(expectedResponseBody, BasicResponse.success("all good"));
    }
}
