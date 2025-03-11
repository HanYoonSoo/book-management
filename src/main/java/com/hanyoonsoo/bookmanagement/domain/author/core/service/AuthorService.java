package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;

import java.util.List;

public interface AuthorService {

    void createAuthor(CreateAuthorRequest request);

    void updateAuthor(UpdateAuthorRequest request, Long authorId);

    void deleteAuthor(Long authorId);

    List<GetAuthorResponse> getAuthors();

    GetAuthorDetailResponse getAuthorDetails(Long authorId);

    Author validateExistsAuthor(Long authorId);
}
