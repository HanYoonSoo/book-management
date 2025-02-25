package com.hanyoonsoo.bookmanagement.domain.author.fake;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthorRepository implements AuthorRepository {

    private final Map<Long, Author> authors = new HashMap<>();
    private Long authorId = 1L;

    @Override
    public void save(Author author) {
        authors.put(authorId++, author);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authors.values().stream()
                .anyMatch(author -> author.getEmail().equals(email));
    }
}
