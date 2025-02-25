package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.impl.AuthorServiceImpl;
import com.hanyoonsoo.bookmanagement.domain.author.fake.MemoryAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    private AuthorService authorService;
    private final AuthorRepository authorRepository = new MemoryAuthorRepository();

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    @DisplayName("이메일이 중복되지 않았다면, 저자 생성에 성공한다.")
    void create_whenEmailNotDuplicated_thenSuccess() throws Exception {
        // given
        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        // when & then
        assertDoesNotThrow(() -> authorService.create(request));
    }

    @Test
    @DisplayName("이메일이 중복되었다면, 저자 생성에 실패한다.")
    void create_whenEmailDuplicated_thenFail() throws Exception {
        // given
        Author author = Author.of("홍길동", "test@example.com");
        authorRepository.save(author);

        CreateAuthorRequest request = CreateAuthorRequest.builder()
                .name("홍길동")
                .email("test@example.com")
                .build();

        // when & then
        assertThrows(AuthorException.class, () -> authorService.create(request));
    }

    @Test
    @DisplayName("이메일이 중복되지 않고 Author Id가 존재한다면, 저자 수정에 성공한다.")
    void update_whenEmailNotDuplicatedAndExistsAuthorId_thenSuccess(){
        //given
        Author author = Author.of("홍길동", "test@example.com");
        long authorId = MemoryAuthorRepository.authorId.get();

        authorRepository.save(author);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when
        authorService.update(request, authorId);

        //then
        Author findAuthor = authorRepository.findByIdOrElseThrow(authorId);
        assertEquals(findAuthor.getName(), request.getName());
        assertEquals(findAuthor.getEmail(), request.getEmail());
    }

    @Test
    @DisplayName("이메일이 중복되었다면, 저자 수정에 실패한다.")
    void update_whenEmailDuplicated_thenFail(){
        //given
        Author author1 = Author.of("홍길동1", "test@example.com");
        long authorId = MemoryAuthorRepository.authorId.get();

        Author author2 = Author.of("홍길동2", "test-update@example.com");

        authorRepository.save(author1);
        authorRepository.save(author2);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when & then
        assertThrows(AuthorException.class, () -> authorService.update(request, authorId));
    }

    @Test
    @DisplayName("전달받은 Id의 Author가 존재하지 않으면, 저자 수정에 실패한다.")
    void update_whenNotFoundAuthor_thenFail(){
        //given
        Author author1 = Author.of("홍길동1", "test@example.com");
        long authorId = MemoryAuthorRepository.authorId.get();

        Author author2 = Author.of("홍길동2", "test-update@example.com");

        authorRepository.save(author1);
        authorRepository.save(author2);

        UpdateAuthorRequest request = UpdateAuthorRequest.builder()
                .name("홍길동수정")
                .email("test-update@example.com")
                .build();

        //when & then
        assertThrows(AuthorException.class, () -> authorService.update(request, authorId));
    }
}
