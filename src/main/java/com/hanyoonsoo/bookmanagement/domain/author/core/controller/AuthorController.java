package com.hanyoonsoo.bookmanagement.domain.author.core.controller;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;

public interface AuthorController {

    ApiResponse<Void> create(CreateAuthorRequest request);
}
