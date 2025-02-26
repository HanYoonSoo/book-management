package com.hanyoonsoo.bookmanagement.domain.book.core.repository;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;

public interface BookRepository {

    void save(Book book);

    boolean existsByIsbn(String isbn);
}
