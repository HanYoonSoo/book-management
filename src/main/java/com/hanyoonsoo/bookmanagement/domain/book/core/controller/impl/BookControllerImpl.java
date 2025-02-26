package com.hanyoonsoo.bookmanagement.domain.book.core.controller.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.controller.BookController;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    @Override
    @PostMapping("/books")
    public ApiResponse<Void> create(@Valid @RequestBody CreateBookRequest request) {
        bookService.create(request);

        return ApiResponse.created(null);
    }
}
