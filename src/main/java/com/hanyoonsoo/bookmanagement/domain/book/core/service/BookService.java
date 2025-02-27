package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;

public interface BookService {

    void create(CreateBookRequest request);

    void update(UpdateBookRequest request, Long bookId);

    void delete(Long bookId);

    PageResponse<GetBookResponse> readBooks(GetBooksCondition condition, int pageNo);
}
