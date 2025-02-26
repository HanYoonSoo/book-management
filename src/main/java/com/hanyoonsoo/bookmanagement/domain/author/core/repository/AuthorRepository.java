package com.hanyoonsoo.bookmanagement.domain.author.core.repository;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;

import java.util.List;

public interface AuthorRepository {

    void save(Author author);

    boolean existsByEmail(String email);

    Author findByIdOrElseThrow(Long authorId);

    List<Author> findAll();
}
