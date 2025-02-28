package com.hanyoonsoo.bookmanagement.domain.book.core.service;

import com.hanyoonsoo.bookmanagement.IntegrationSupporter;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.fixture.AuthorFixture;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.SortCriteria;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.fixture.BookFixture;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceIntegrationTest extends IntegrationSupporter {

    // Service Bean
    @Autowired private BookService bookService;

    // Repository Bean
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Test
    @DisplayName("존재하는 저자이며 isbn이 중복되지 않았다면, 도서 생성에 성공한다.")
    void create_whenExistsAuthorAndIsbnNotDuplicated_thenSuccess() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();
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
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
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
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
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
        Author author = AuthorFixture.createAuthor();
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
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
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
    @Transactional
    void delete_whenExistsBook_thenSuccess() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = Book.builder()
                .title("테스트 타이틀")
                .description("테스트 설명")
                .isbn("1234567890")
                .publicationDate(LocalDate.now())
                .author(author)
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
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        //when & then
        assertThrows(BookException.class, () -> bookService.delete(1L));
    }

    @Test
    @DisplayName("필터링 없는 도서 Pagination 조회에 성공한다.")
    void readBooks_whenNoFilter_thenReturnPaginatedBooksSuccess() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
        bookRepository.save(book);

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(new GetBooksCondition(), 0);

        //then
        assertThat(response.getData()).isNotNull();
    }

    @Test
    @DisplayName("필터링 있는 도서 Pagination 조회에 성공한다.")
    void readBooks_whenFilterApplied_thenReturnPaginatedBooksSuccess() {
        //given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
        bookRepository.save(book);

        GetBooksCondition condition = new GetBooksCondition();

        condition.setTitle("타이틀");

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        assertThat(response.getData()).isNotNull();
    }

    @Test
    @DisplayName("도서 출판일 오름차순 Pagination 조회에 성공한다.")
    void readBooks_whenPublicationDateAsc_thenReturnPaginatedBooksSuccess() {
        //given
        saveAuthorAndBookForTest();

        GetBooksCondition condition = new GetBooksCondition();
        condition.setSortCriteria(SortCriteria.PUBLICATION_DATE_ASC);

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        for (int i = 1; i <= 2; i++) {
            GetBookResponse bookResponse = response.getData().get(i - 1);

            assertEquals(bookResponse.getTitle(), "테스트 타이틀" + i);
        }
    }

    @Test
    @DisplayName("도서 출판일 내림차순 Pagination 조회에 성공한다.")
    void readBooks_whenPublicationDateDesc_thenReturnPaginatedBooksSuccess() {
        //given
        saveAuthorAndBookForTest();

        GetBooksCondition condition = new GetBooksCondition();
        condition.setSortCriteria(SortCriteria.PUBLICATION_DATE_DESC);

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        for (int i = 2; i >= 1; i--) {
            GetBookResponse bookResponse = response.getData().get(2 - i);

            assertEquals(bookResponse.getTitle(), "테스트 타이틀" + i);
        }
    }

    @Test
    @DisplayName("도서 타이틀 오름차순 Pagination 조회에 성공한다.")
    void readBooks_whenTitleAsc_thenReturnPaginatedBooksSuccess() {
        //given
        saveAuthorAndBookForTest();

        GetBooksCondition condition = new GetBooksCondition();
        condition.setSortCriteria(SortCriteria.TITLE_ASC);

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        for (int i = 1; i <= 2; i++) {
            GetBookResponse bookResponse = response.getData().get(i - 1);

            assertEquals(bookResponse.getTitle(), "테스트 타이틀" + i);
        }
    }

    @Test
    @DisplayName("도서 타이틀 내림차순 Pagination 조회에 성공한다.")
    void readBooks_whenTitleDesc_thenReturnPaginatedBooksSuccess() {
        //given
        saveAuthorAndBookForTest();

        GetBooksCondition condition = new GetBooksCondition();
        condition.setSortCriteria(SortCriteria.TITLE_DESC);

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        for (int i = 2; i >= 1; i--) {
            GetBookResponse bookResponse = response.getData().get(2 - i);

            assertEquals(bookResponse.getTitle(), "테스트 타이틀" + i);
        }
    }

    @Test
    @DisplayName("필터링에 걸리는 도서가 없다면 빈값 반환에 성공한다.")
    void readBooks_whenFilterNoMatch_thenReturnEmptyPage() {
        //given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
        bookRepository.save(book);

        GetBooksCondition condition = new GetBooksCondition();

        condition.setTitle("조회X");

        //when
        PageResponse<GetBookResponse> response = bookService.readBooks(condition, 0);

        //then
        assertThat(response.getData()).isEmpty();
    }

    @Test
    @DisplayName("Book Id가 존재한다면, 도서 상세 조회에 성공한다.")
    void readBookDetail_whenExistsBook_thenSuccess() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book = BookFixture.createBook(author);
        bookRepository.save(book);

        //when
        GetBookDetailResponse response = bookService.readBookDetail(1L);

        //then
        assertEquals("테스트 타이틀", response.getTitle());
        assertEquals("1234567890", response.getIsbn());
    }

    @Test
    @DisplayName("Book Id가 존재하지 않는다면, 도서 상세 조회에 실패한다.")
    void readBookDetail_whenNotFoundBook_thenFail() throws Exception {
        //when & then
        assertThrows(BookException.class, () -> bookService.readBookDetail(1L));
    }

    private void saveAuthorAndBookForTest() {
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        Book book1 = Book.of(
                "테스트 타이틀1",
                "테스트 설명1",
                "1234567890",
                LocalDate.now(),
                author
        );
        bookRepository.save(book1);

        Book book2 = Book.of(
                "테스트 타이틀2",
                "테스트 설명2",
                "1234567990",
                LocalDate.now().plusDays(1L),
                author
        );
        bookRepository.save(book2);
    }
}
