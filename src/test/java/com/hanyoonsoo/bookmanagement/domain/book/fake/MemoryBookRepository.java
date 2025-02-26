package com.hanyoonsoo.bookmanagement.domain.book.fake;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;

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
}
