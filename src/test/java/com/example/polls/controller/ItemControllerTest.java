package com.example.polls.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(ItemController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    Logger logger = LoggerFactory.getLogger(ItemControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @Test
    void create() throws Exception {
        Map<String, String> urlVars = new HashMap<String, String>();
        urlVars.put("description", "pizza");
        urlVars.put("price", "12");
        urlVars.put("categoryId", "1");
        JSONObject json = new JSONObject(urlVars);

        MvcResult result = mvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();
        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    void retrieve() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/items");
        MvcResult result = mvc.perform(request).andReturn();
        logger.info(result.getResponse().getContentAsString());
    }
}
