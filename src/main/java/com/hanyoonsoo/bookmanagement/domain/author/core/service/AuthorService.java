package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;

public interface AuthorService {

    void create(CreateAuthorRequest request);
}
