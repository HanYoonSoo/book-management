package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;

public interface BookService {

    void create(CreateBookRequest request);
}
