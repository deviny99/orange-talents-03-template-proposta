package com.zup.academy.global.controller;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public interface MvcRest {

    MvcResult postEndpoint(MockMvc mockMvc, String url, String contentBody) throws Exception;
    MvcResult getEndpoint(MockMvc mockMvc, String url, String contentBody) throws Exception;

}
