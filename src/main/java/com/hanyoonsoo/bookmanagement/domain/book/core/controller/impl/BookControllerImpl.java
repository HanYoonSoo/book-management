package com.hanyoonsoo.bookmanagement.domain.book.core.controller.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.controller.BookController;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @PatchMapping("/books/{bookId}")
    public ApiResponse<Void> update(
            @Valid @RequestBody UpdateBookRequest request, @PathVariable("bookId") Long bookId
    ) {
        bookService.update(request, bookId);

        return ApiResponse.ok(null);
    }

    @Override
    @DeleteMapping("/books/{bookId}")
    public ApiResponse<Void> delete(@PathVariable("bookId") Long bookId) {
        bookService.delete(bookId);

        return ApiResponse.ok(null);
    }

    @Override
    @GetMapping("/books")
    public ApiResponse<PageResponse<GetBookResponse>> readBooks(
            GetBooksCondition condition,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo)
    {
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, pageNo);

        return ApiResponse.ok(response);
    }
}
