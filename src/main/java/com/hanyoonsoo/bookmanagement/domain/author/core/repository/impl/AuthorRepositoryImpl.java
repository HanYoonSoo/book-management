package com.hanyoonsoo.bookmanagement.domain.author.core.repository.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.jpa.AuthorJpaRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public void save(Author author) {
        authorJpaRepository.save(author);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authorJpaRepository.existsByEmail(email);
    }

    @Override
    public Author findByIdOrElseThrow(Long authorId) {
        return authorJpaRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException(ErrorCode.NOT_FOUND, "Not Found Author"));
    }

}
