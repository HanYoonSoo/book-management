package com.hanyoonsoo.bookmanagement.domain.author.fake;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryAuthorRepository implements AuthorRepository {

    private final Map<Long, Author> authors = new HashMap<>();
    public Long authorId = 1L;


    @Override
    public void save(Author author) {
        authors.put(authorId++, author);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authors.values().stream()
                .anyMatch(author -> author.getEmail().equals(email));
    }

    @Override
    public Author findByIdOrElseThrow(Long authorId) {
        return Optional.ofNullable(authors.get(authorId))
                .orElseThrow(() -> new AuthorException(ErrorCode.NOT_FOUND, "Not Found Author"));
    }

    @Override
    public List<Author> findAll() {
        return authors.keySet().stream()
                .sorted()
                .map(authors::get)
                .toList();
    }

}
