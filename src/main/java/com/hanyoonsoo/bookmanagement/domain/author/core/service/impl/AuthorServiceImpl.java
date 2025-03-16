package com.hanyoonsoo.bookmanagement.domain.author.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.util.StringUtils.*;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void createAuthor(CreateAuthorRequest request) {
        validateDuplicatedEmail(request.getEmail());

        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(UpdateAuthorRequest request, Long authorId) {
        Author author = validateExistsAuthor(authorId);

        if(hasText(request.getName())) author.modifyName(request.getName());

        if(hasText(request.getEmail())){
            validateDuplicatedEmail(request.getEmail());
            author.modifyEmail(request.getEmail());
        }
    }

    /**
     * 저자, 도서 삭제 과정에서 Soft Delete를 활용.
     * 저자 관련 도서 정보 우선 삭제 후, 저자 삭제
     * 저자 삭제 시 email값: deleted_(UUID)_email
     * 도서 삭제 시 isbn값: deleted_(UUID)_isbn
     */
    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        Author author = validateExistsAuthor(authorId);

        bookRepository.deleteByAuthorId(authorId);
        authorRepository.delete(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAuthorResponse> getAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(GetAuthorResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GetAuthorDetailResponse getAuthorDetails(Long authorId) {
        Author author = validateExistsAuthor(authorId);

        return GetAuthorDetailResponse.from(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Author validateExistsAuthor(Long authorId) {
        return authorRepository.findByIdOrElseThrow(authorId);
    }

    private void validateDuplicatedEmail(String email) {
        boolean exists = authorRepository.existsByEmail(email);

        if(exists) throw new AuthorException(ErrorCode.DUPLICATED, "Already Exists Email");
    }
}
