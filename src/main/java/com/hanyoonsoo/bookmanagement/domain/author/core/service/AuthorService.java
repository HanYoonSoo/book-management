package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;

public interface AuthorService {

    void create(CreateAuthorRequest request);

    void update(UpdateAuthorRequest request, Long authorId);
}
