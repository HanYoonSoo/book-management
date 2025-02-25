package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;

import java.util.List;

public interface AuthorService {

    void create(CreateAuthorRequest request);

    void update(UpdateAuthorRequest request, Long authorId);

    List<GetAuthorResponse> readAuthors();

    GetAuthorResponse readAuthorDetail(Long authorId);
}
