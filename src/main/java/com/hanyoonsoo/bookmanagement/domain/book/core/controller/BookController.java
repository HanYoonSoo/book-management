package com.hanyoonsoo.bookmanagement.domain.book.core.controller;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;

public interface BookController {

    ApiResponse<Void> create(CreateBookRequest request);
}
