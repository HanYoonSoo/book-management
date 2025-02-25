package com.hanyoonsoo.bookmanagement.domain.author.core.repository;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;

public interface AuthorRepository {

    void save(Author author);

    boolean existsByEmail(String email);
}
