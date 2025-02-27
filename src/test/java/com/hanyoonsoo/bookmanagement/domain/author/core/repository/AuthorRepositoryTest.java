//package com.hanyoonsoo.bookmanagement.domain.author.core.repository;
//
//import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
//import com.hanyoonsoo.bookmanagement.domain.author.core.repository.jpa.AuthorJpaRepository;
//import com.hanyoonsoo.bookmanagement.global.config.JpaConfig;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Import(JpaConfig.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//class AuthorRepositoryTest {
//
//    @Autowired
//    private AuthorJpaRepository authorJpaRepository;
//
//    @Test
//    @DisplayName("이메일이 중복되지 않으면, 저자 생성에 성공한다.")
//    void emailNotDuplicated_thenSaveSuccess() throws Exception {
//        //given
//        Author author = Author.builder()
//                .name("홍길동")
//                .email("test@example.com")
//                .build();
//
//        //when & then
//        assertDoesNotThrow(() -> authorJpaRepository.save(author));
//    }
//
//    @Test
//    @DisplayName("이메일이 중복이면, 저자 생성에 실패한다.")
//    void emailDuplicated_thenSaveFail() throws Exception {
//        //given
//        Author author = Author.of("홍길동", "test-fail@example.com");
//        authorJpaRepository.save(author);
//
//        //when & then
//        assertThrows(DataIntegrityViolationException.class,
//                () -> authorJpaRepository.save(Author.of("홍길동", "test-fail@example.com")));
//    }
//
//    @Test
//    @DisplayName("저자가 있다면, 저자를 찾는데 성공한다.")
//    void whenExistsAuthor_thenFindSuccess() throws Exception {
//        //given
//        Author author = Author.of("홍길동", "test@example.com");
//        authorJpaRepository.save(author);
//
//        //when & then
//        assertDoesNotThrow(() -> authorJpaRepository.findById(author.getId()));
//    }
//}
