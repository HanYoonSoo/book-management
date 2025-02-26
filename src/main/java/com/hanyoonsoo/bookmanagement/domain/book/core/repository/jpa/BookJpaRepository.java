package com.hanyoonsoo.bookmanagement.domain.book.core.repository.jpa;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
