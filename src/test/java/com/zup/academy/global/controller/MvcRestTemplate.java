package com.zup.academy.global.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class MvcRestTemplate implements MvcRest {

    @Override
    public MvcResult postEndpoint(MockMvc mockMvc, String url, String contentBody) throws Exception {
        return  mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(contentBody))
                .andReturn();
    }

    @Override
    public MvcResult getEndpoint(MockMvc mockMvc, String url, String contentBody) throws Exception {
        return  mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(contentBody))
                .andReturn();
    }

}
