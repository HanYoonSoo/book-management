package com.hanyoonsoo.bookmanagement.domain.author.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.exception.AuthorException;
import com.hanyoonsoo.bookmanagement.domain.author.core.repository.AuthorRepository;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void create(CreateAuthorRequest request) {
        validateDuplicatedEmail(request.getEmail());

        Author author = Author.of(request.getName(), request.getEmail());
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void update(UpdateAuthorRequest request, Long authorId) {
        Author author = validateExistsAuthor(authorId);

        if(StringUtils.hasText(request.getName())) author.modifyName(request.getName());

        if(StringUtils.hasText(request.getEmail())){
            validateDuplicatedEmail(request.getEmail());
            author.modifyEmail(request.getEmail());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAuthorResponse> readAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(GetAuthorResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GetAuthorResponse readAuthorDetail(Long authorId) {
        Author author = validateExistsAuthor(authorId);

        return GetAuthorResponse.from(author);
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
