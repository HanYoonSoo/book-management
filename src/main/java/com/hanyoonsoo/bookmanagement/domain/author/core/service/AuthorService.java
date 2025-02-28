package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;

import java.util.List;

public interface AuthorService {

    void create(CreateAuthorRequest request);

    void update(UpdateAuthorRequest request, Long authorId);

    void delete(Long authorId);

    List<GetAuthorResponse> readAuthors();

    GetAuthorDetailResponse readAuthorDetail(Long authorId);

    Author validateExistsAuthor(Long authorId);
}
