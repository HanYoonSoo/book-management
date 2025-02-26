package com.hanyoonsoo.bookmanagement.domain.book.core.repository.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.jpa.BookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;
}
