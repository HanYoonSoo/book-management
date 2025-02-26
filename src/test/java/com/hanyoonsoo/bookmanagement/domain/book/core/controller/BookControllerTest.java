package com.hanyoonsoo.bookmanagement.domain.book.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    @DisplayName("도서 생성에 성공하면, 201 상태코드를 전달 받는다.")
    void create_whenSuccess_thenReturnStatus201() throws Exception {
        //given
        CreateBookRequest request = CreateBookRequest.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .publication_date(LocalDate.now())
                .isbn("1234567890")
                .authorId(1L)
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(bookService)
                .create(any());

        //when & then
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("필수값이 전달되지 않았다면, 400 상태코드를 전달 받는다.")
    void create_whenBlankRequiredFields_thenReturnStatus400() throws Exception {
        //given
        CreateBookRequest request = CreateBookRequest.builder().build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(bookService)
                .create(any());

        //when & then
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("isbn 형식이 잘못되었다면, 400 상태코드를 전달 받는다.")
    void create_whenNotValidIsbn_thenReturnStatus400() throws Exception {
        //given
        CreateBookRequest request = CreateBookRequest.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .publication_date(LocalDate.now())
                .isbn("099001002")
                .authorId(1L)
                .build();

        String requestBody = objectMapper.writeValueAsString(request);

        doNothing()
                .when(bookService)
                .create(any());

        //when & then
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
