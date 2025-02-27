package com.hanyoonsoo.bookmanagement.domain.book.core.repository;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBookRepository {

    Page<Book> findPagedBooksByCondition(GetBooksCondition condition, Pageable pageable);
}
