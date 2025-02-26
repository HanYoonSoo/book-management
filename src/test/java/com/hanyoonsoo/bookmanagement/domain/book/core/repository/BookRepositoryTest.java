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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void whenIsbnNotDuplicated_thenSaveSuccess() throws Exception {
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
    void whenIsbnDuplicated_thenSaveFail() throws Exception {
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

    @Test
    @DisplayName("도서의 저자가 존재하지 않는다면, 도서 생성에 실패한다.")
    void whenNotFoundBook_thenSaveFail() throws Exception {
        //given
        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                null
        );

        //when & then
        assertThrows(DataIntegrityViolationException.class,
                () -> bookJpaRepository.save(book)
        );
    }

    @Test
    @DisplayName("존재하는 도서이며 isbn이 중복되지 않았다면, JPA의 더티 체킹을 사용한 도서 수정에 성공한다.")
    void whenExistsBookAndNotDuplicatedIsbn_thenJpaDirtyCheckingUpdateSuccess() throws Exception {
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

        Book savedBook = bookJpaRepository.save(book);

        //when
        savedBook.modifyIsbn("2345678980");
        savedBook.modifyTitle("테스트 수정 타이틀");

        //then
        Book updatedBook = bookJpaRepository.findById(savedBook.getId()).orElse(null);
        assertEquals("2345678980", updatedBook.getIsbn());
        assertEquals("테스트 수정 타이틀", updatedBook.getTitle());
    }

    @Test
    @DisplayName("존재하는 도서라면, 삭제에 성공한다.")
    void whenExistsBook_thenDeleteSuccess() throws Exception {
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
        Book savedBook = bookJpaRepository.save(book);

        //when
        bookJpaRepository.delete(savedBook);

        //then
        Optional<Book> optionalBook = bookJpaRepository.findById(savedBook.getId());
        assertThat(optionalBook.isEmpty()).isTrue();
    }
}
