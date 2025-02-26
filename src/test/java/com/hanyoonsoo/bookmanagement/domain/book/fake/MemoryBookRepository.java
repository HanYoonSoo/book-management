package com.hanyoonsoo.bookmanagement.domain.book.fake;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryBookRepository implements BookRepository {

    private final Map<Long, Book> books = new HashMap<>();
    public Long bookId = 1L;

    @Override
    public void save(Book book) {
        books.put(bookId++, book);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.values().stream()
                .anyMatch(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public Book findByIdOrElseThrow(Long bookId) {
        return Optional.ofNullable(books.get(bookId))
                .orElseThrow(() -> new BookException(ErrorCode.NOT_FOUND, "Not Found Book"));
    }

    @Override
    public void delete(Book book) {
        books.entrySet().removeIf(entry ->
                entry.getValue().getId().equals(book.getId())
        );
    }
}
