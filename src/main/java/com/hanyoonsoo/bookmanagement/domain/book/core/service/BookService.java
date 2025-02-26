package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;

public interface BookService {

    void create(CreateBookRequest request);

    void update(UpdateBookRequest request, Long bookId);
}
