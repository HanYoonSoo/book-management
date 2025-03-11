package com.hanyoonsoo.bookmanagement.domain.author.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        //given
        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .createAuthor(any());

        //when & then
        mockMvc.perform(post("/authors")
                .contentType("application/json")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("필수값이 전달되지 않았다면, 400 상태코드를 전달 받는다.")
    void create_whenBlankRequiredFields_thenReturnStatus400() throws Exception {
        //given
        CreateAuthorRequest request = CreateAuthorRequest.builder().build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .createAuthor(any());

        //when & then
        mockMvc.perform(post("/authors")
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("저자 수정에 성공하면 200 상태코드를 전달 받는다.")
    void update_whenSuccess_thenReturnStatus200() throws Exception {
        //given
        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .updateAuthor(any(), any());

        //when & then
        mockMvc.perform(patch("/authors/1")
                .contentType("application/json")
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일 형식이 잘못되었다면, 400 상태코드를 전달 받는다.")
    void update_whenNotValidEmail_thenReturnStatus400() throws Exception {
        //given
        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update#example.com")
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(authorService)
                .updateAuthor(any(), any());

        //when & then
        mockMvc.perform(patch("/authors/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("저자 목록 조회에 성공하면 200 상태코드를 전달 받는다.")
    void readAuthors_whenSuccess_thenReturnStatus200() throws Exception {
        //given
        List<GetAuthorResponse> response = List.of(
                GetAuthorResponse.builder()
                        .authorId(1L)
                        .name("홍길동1")
                        .email("test1@example.com")
                        .build(),
                GetAuthorResponse.builder()
                        .authorId(2L)
                        .name("홍길동2")
                        .email("test2@example.com")
                        .build()
        );

        given(authorService.getAuthors()).willReturn(response);

        //when & then
        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].authorId").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("홍길동1"))
                .andExpect(jsonPath("$.data[0].email").value("test1@example.com"))
                .andExpect(jsonPath("$.data[1].authorId").value(2L))
                .andExpect(jsonPath("$.data[1].name").value("홍길동2"))
                .andExpect(jsonPath("$.data[1].email").value("test2@example.com"));
    }

    @Test
    @DisplayName("저자 상세 조회에 성공하면 200 상태코드를 전달 받는다.")
    void readAuthorDetail_whenSuccess_thenReturnStatus200() throws Exception {
        //given
        GetAuthorDetailResponse response = GetAuthorDetailResponse.builder()
                .authorId(1L)
                .name("홍길동1")
                .email("test1@example.com")
                .build();

        given(authorService.getAuthorDetails(1L)).willReturn(response);

        //when & then
        mockMvc.perform(get("/authors/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
