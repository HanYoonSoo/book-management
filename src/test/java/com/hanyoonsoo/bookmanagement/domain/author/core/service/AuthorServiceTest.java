package com.hanyoonsoo.bookmanagement.domain.author.core.service;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
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
    @DisplayName("이메일이 중복되지 않았을 경우 저자 생성에 성공한다.")
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
    @DisplayName("이메일이 중복되었을 경우 저자 생성에 실패한다.")
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
}
