package com.hanyoonsoo.bookmanagement.domain.book.core.repository.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.jpa.BookJpaRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookJpaRepository.existsByIsbn(isbn);
    }

    @Override
    public Book findByIdOrElseThrow(Long bookId) {
        return bookJpaRepository.findById(bookId)
                .orElseThrow(() -> new BookException(ErrorCode.NOT_FOUND, "Not Found Book"));
    }

    @Override
    public void delete(Book book) {
        bookJpaRepository.delete(book);
    }

}
