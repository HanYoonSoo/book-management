package com.hanyoonsoo.bookmanagement.domain.book.core.repository;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.jpa.AuthorJpaRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.jpa.BookJpaRepository;
import com.hanyoonsoo.bookmanagement.global.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Import(JpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Test
    @DisplayName("isbn이 중복되지 않으면, 도서 생성에 성공한다.")
    void isbnNotDuplicated_thenSaveSuccess() throws Exception {
        //given
        Author author = Author.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        authorJpaRepository.save(author);

        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );

        //when & then
        assertDoesNotThrow(() -> bookJpaRepository.save(book));
    }

    @Test
    @DisplayName("isbn이 중복되면, 도서 생성에 실패한다.")
    void isbnDuplicated_thenSaveFail() throws Exception {
        //given
        Author author = Author.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        authorJpaRepository.save(author);

        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );

        bookJpaRepository.save(book);

        //when & then
        assertThrows(DataIntegrityViolationException.class,
                () -> bookJpaRepository.save(Book.of(
                        "테스트 타이틀2",
                        "테스트 설명2",
                        "1234567890",
                        LocalDate.now(),
                        author)
                )
        );
    }
}
