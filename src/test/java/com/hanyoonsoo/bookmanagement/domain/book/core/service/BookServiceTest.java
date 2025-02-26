package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.impl.AuthorServiceImpl;
import com.hanyoonsoo.bookmanagement.domain.author.fake.MemoryAuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.impl.BookServiceImpl;
import com.hanyoonsoo.bookmanagement.domain.book.fake.MemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        authorRepository = new MemoryAuthorRepository();
        bookRepository = new MemoryBookRepository();

        AuthorService authorService = new AuthorServiceImpl(authorRepository);
        bookService = new BookServiceImpl(authorService, bookRepository);
    }

    @Test
    @DisplayName("존재하는 저자이며 isbn이 중복되지 않았다면, 도서 생성에 성공한다.")
    void create_whenExistsAuthorAndIsbnNotDuplicated_thenSuccess() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        CreateBookRequest request = CreateBookRequest.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .publicationDate(LocalDate.now())
                .isbn("1234567890")
                .authorId(1L)
                .build();

        //when & then
        assertDoesNotThrow(() -> bookService.create(request));
    }

    @Test
    @DisplayName("관련 저자가 존재하지 않는다면, 도서 생성에 실패한다.")
    void create_whenNotFoundAuthor_thenFail() throws Exception {
        //given
        CreateBookRequest request = CreateBookRequest.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .publicationDate(LocalDate.now())
                .isbn("1234567890")
                .authorId(1L)
                .build();

        //when & then
        assertThrows(AuthorException.class, () -> bookService.create(request));
    }

    @Test
    @DisplayName("isbn이 중복되었다면, 도서 생성에 실패한다.")
    void create_whenIsbnDuplicated_thenFail() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );
        bookRepository.save(book);

        CreateBookRequest request = CreateBookRequest.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .publicationDate(LocalDate.now())
                .isbn("1234567890")
                .authorId(1L)
                .build();

        //when & then
        assertThrows(BookException.class, () -> bookService.create(request));
    }

    @Test
    @DisplayName("존재하는 도서이며 isbn이 중복되지 않았다면, 수정에 성공한다.")
    void update_whenExistsBookAndIsbnNotDuplicated_thenSuccess() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );
        bookRepository.save(book);

        UpdateBookRequest request = UpdateBookRequest.builder()
                .title("테스트 수정 타이틀")
                .description("테스트 수정 설명")
                .isbn("2345678980")
                .publicationDate(LocalDate.now())
                .build();

        //when & then
        assertDoesNotThrow(() -> bookService.update(request, 1L));
    }

    @Test
    @DisplayName("존재하지 않는 도서라면, 수정에 실패한다.")
    void update_whenNotFoundBook_thenFail() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        UpdateBookRequest request = UpdateBookRequest.builder()
                .title("테스트 수정 타이틀")
                .description("테스트 수정 설명")
                .isbn("2345678980")
                .publicationDate(LocalDate.now())
                .build();

        //when & then
        assertThrows(BookException.class, () -> bookService.update(request, 1L));
    }

    @Test
    @DisplayName("isbn이 중복되었다면, 수정에 실패한다.")
    void update_whenIsbnDuplicated_thenFail() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        Book book = Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );
        bookRepository.save(book);

        UpdateBookRequest request = UpdateBookRequest.builder()
                .title("테스트 수정 타이틀")
                .description("테스트 수정 설명")
                .isbn("1234567890")
                .publicationDate(LocalDate.now())
                .build();

        //when & then
        assertThrows(BookException.class, () -> bookService.update(request, 1L));
    }

    @Test
    @DisplayName("존재하는 도서라면, 삭제에 성공한다.")
    void delete_whenExistsBook_thenSuccess() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        Book book = Book.builder()
                .id(1L)
                .title("테스트 타이틀")
                .description("테스트 설명")
                .isbn("1234567890")
                .publicationDate(LocalDate.now())
                .build();
        bookRepository.save(book);

        //when
        bookService.delete(1L);

        //then
        assertThrows(BookException.class, () -> bookRepository.findByIdOrElseThrow(1L));
    }

    @Test
    @DisplayName("존재하지 않는 도서라면, 삭제에 실패한다.")
    void delete_whenNotFoundBook_thenFail() throws Exception {
        //given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        //when & then
        assertThrows(BookException.class, () -> bookService.delete(1L));
    }

}
