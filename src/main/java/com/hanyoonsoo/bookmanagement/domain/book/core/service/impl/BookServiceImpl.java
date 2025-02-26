package com.hanyoonsoo.bookmanagement.domain.book.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void create(CreateBookRequest request) {
        Author author = authorService.validateExistsAuthor(request.getAuthorId());
        validateDuplicatedIsbn(request.getIsbn());

        Book book = Book.of(
                request.getTitle(),
                request.getDescription(),
                request.getIsbn(),
                request.getPublicationDate(),
                author
        );

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(UpdateBookRequest request, Long bookId) {
        Book book = validateExistsBook(bookId);

        String isbn = request.getIsbn();
        String title = request.getTitle();
        String description = request.getDescription();
        LocalDate publicationDate = request.getPublicationDate();

        if(hasText(isbn)){
            validateDuplicatedIsbn(isbn);
            book.modifyIsbn(isbn);
        }

        if(hasText(title)) book.modifyTitle(title);
        if(hasText(description)) book.modifyDescription(description);
        if(publicationDate != null) book.modifyPublicationDate(publicationDate);
    }

    private Book validateExistsBook(Long bookId) {
        return bookRepository.findByIdOrElseThrow(bookId);
    }

    private void validateDuplicatedIsbn(String isbn) {
        boolean exists = bookRepository.existsByIsbn(isbn);

        if(exists) throw new BookException(ErrorCode.DUPLICATED, "Already Exists Isbn");
    }
}
