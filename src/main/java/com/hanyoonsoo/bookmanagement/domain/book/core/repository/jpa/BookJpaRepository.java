package com.hanyoonsoo.bookmanagement.domain.book.core.repository.jpa;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.CustomBookRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Long>, CustomBookRepository {

    boolean existsByIsbn(String isbn);

    @Query("select b from Book b join fetch b.author where b.id = ?1")
    Optional<Book> findByIdWithAuthor(Long bookId);

    @Modifying
    @Query("update Book b set b.deletedAt = CURRENT_TIMESTAMP, " +
            "b.isbn = concat('deleted_', function('uuid')) where b.author.id = ?1"
    )
    void deleteByAuthorId(Long authorId);
}
