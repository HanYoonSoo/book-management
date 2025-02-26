package com.hanyoonsoo.bookmanagement.domain.book.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    @Override
    public void create(CreateBookRequest request) {
        Author author = authorService.validateExistsAuthor(request.getAuthorId());
        validateDuplicatedIsbn(request.getIsbn());

        Book book = Book.of(
                request.getTitle(),
                request.getDescription(),
                request.getIsbn(),
                request.getPublication_date(),
                author
        );

        bookRepository.save(book);
    }

    private void validateDuplicatedIsbn(String isbn) {
        boolean exists = bookRepository.existsByIsbn(isbn);

        if(exists) throw new BookException(ErrorCode.DUPLICATED, "Already Exists Isbn");
    }
}
