package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;

public interface BookService {

    void createBook(CreateBookRequest request);

    void updateBook(UpdateBookRequest request, Long bookId);

    void deleteBook(Long bookId);

    PageResponse<GetBookResponse> getBooksWithPagination(GetBooksCondition condition, int pageNo);

    GetBookDetailResponse getBookDetails(Long bookId);
}
