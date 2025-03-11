package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.impl.AuthorServiceImpl;
import com.hanyoonsoo.bookmanagement.domain.author.fake.MemoryAuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.fixture.AuthorFixture;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.fake.MemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        authorRepository = new MemoryAuthorRepository();
        bookRepository = new MemoryBookRepository();

        authorService = new AuthorServiceImpl(authorRepository, bookRepository);
    }

    @Test
    @DisplayName("이메일이 중복되지 않았다면, 저자 생성에 성공한다.")
    void createAuthor_whenEmailNotDuplicated_thenSuccess() throws Exception {
        // given
        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        // when & then
        assertDoesNotThrow(() -> authorService.createAuthor(request));
    }

    @Test
    @DisplayName("이메일이 중복되었다면, 저자 생성에 실패한다.")
    void createAuthor_whenEmailDuplicated_thenFail() throws Exception {
        // given
        Author author = AuthorFixture.createAuthor();
        authorRepository.save(author);

        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        // when & then
        assertThrows(AuthorException.class, () -> authorService.createAuthor(request));
    }

    @Test
    @DisplayName("이메일이 중복되지 않고 Author Id가 존재한다면, 저자 수정에 성공한다.")
    void updateAuthor_whenEmailNotDuplicatedAndExistsAuthorId_thenSuccess() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();

        authorRepository.save(author);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when
        authorService.updateAuthor(request, 1L);

        //then
        Author findAuthor = authorRepository.findByIdOrElseThrow(1L);
        assertEquals(findAuthor.getName(), request.getName());
        assertEquals(findAuthor.getEmail(), request.getEmail());
    }

    @Test
    @DisplayName("이메일이 중복되었다면, 저자 수정에 실패한다.")
    void updateAuthor_whenEmailDuplicated_thenFail() throws Exception {
        //given
        Author author1 = Author.of("홍길동1", "test@example.com");

        Author author2 = Author.of("홍길동2", "test-update@example.com");

        authorRepository.save(author1);
        authorRepository.save(author2);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when & then
        assertThrows(AuthorException.class, () -> authorService.updateAuthor(request, 1L));
    }

    @Test
    @DisplayName("전달받은 Id의 저자가 존재하지 않으면, 저자 수정에 실패한다.")
    void updateAuthor_whenNotFoundAuthor_thenFail() throws Exception {
        //given
        Author author1 = Author.of("홍길동1", "test@example.com");

        Author author2 = Author.of("홍길동2", "test-update@example.com");

        authorRepository.save(author1);
        authorRepository.save(author2);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when & then
        assertThrows(AuthorException.class, () -> authorService.updateAuthor(request, 1L));
    }

    @Test
    @DisplayName("저자 목록 조회에 성공한다.")
    void getAuthors_Success() throws Exception {
        //given
        Author author1 = Author.of("홍길동1", "test1@example.com");
        Author author2 = Author.of("홍길동2", "test2@example.com");
        Author author3 = Author.of("홍길동3", "test3@example.com");

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        //when
        List<GetAuthorResponse> authors = authorService.getAuthors();

        //then
        for(int i = 0; i < 3; i++){
            assertEquals(authors.get(i).getName(), "홍길동" + (i + 1));
            assertEquals(authors.get(i).getEmail(), "test" + (i + 1) + "@example.com");
        }
    }

    @Test
    @DisplayName("저자 상세 조회에 성공한다.")
    void getAuthorDetails_Success() throws Exception {
        //given
        Author author = AuthorFixture.createAuthor();

        authorRepository.save(author);

        //when
        GetAuthorDetailResponse response = authorService.getAuthorDetails(1L);

        //then
        assertEquals(author.getEmail(), response.getEmail());
        assertEquals(author.getName(), response.getName());
    }

    @Test
    @DisplayName("전달받은 Id의 저자가 존재하면, 삭제에 성공한다.")
    void deleteAuthor_whenExistsAuthor_thenSuccess() throws Exception {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("test@example.com")
                .build();

        authorRepository.save(author);

        //when
        authorService.deleteAuthor(1L);

        //then
        assertThrows(AuthorException.class, () -> authorRepository.findByIdOrElseThrow(1L));
    }
}
