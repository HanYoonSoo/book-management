package com.hanyoonsoo.bookmanagement.domain.author.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthorService authorService;

    @Test
    @DisplayName("저자 생성에 성공하면 201 상태코드를 전달 받는다.")
    void create_whenSuccess_thenReturnStatus201() throws Exception {
        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .create(any());

        mockMvc.perform(post("/authors")
                .contentType("application/json")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("저자 수정에 성공하면 200 상태코드를 전달 받는다.")
    void update_whenSuccess_thenReturnStatus200() throws Exception {
        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길도수정")
                .email("test-update@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .update(any(), any());

        mockMvc.perform(patch("/authors/1")
                .contentType("application/json")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
