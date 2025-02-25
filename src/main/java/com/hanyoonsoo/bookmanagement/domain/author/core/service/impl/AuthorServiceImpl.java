package com.hanyoonsoo.bookmanagement.domain.author.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void create(CreateAuthorRequest request) {
        validateDuplicatedEmail(request.getEmail());

        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

    private void validateDuplicatedEmail(String email) {
        boolean exists = authorRepository.existsByEmail(email);

        if(exists) throw new AuthorException(ErrorCode.NOT_VALID, "Already Exists Email");
    }
}
