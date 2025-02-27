package com.hanyoonsoo.bookmanagement.domain.book.core.service.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    private final int booksPageSize = 10;

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

    @Override
    @Transactional
    public void delete(Long bookId) {
        Book book = validateExistsBook(bookId);

        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<GetBookResponse> readBooks(GetBooksCondition condition, int pageNo) {
        pageNo = Math.max(pageNo, 0);

        Pageable pageable = PageRequest.of(pageNo, booksPageSize);

        Page<Book> books = bookRepository.findPagedBooksByCondition(condition, pageable);

        return PageResponse.of(books, GetBookResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public GetBookDetailResponse readBookDetail(Long bookId) {
        Book book = validateExistsBook(bookId);

        return GetBookDetailResponse.from(book);
    }

    private Book validateExistsBook(Long bookId) {
        return bookRepository.findByIdOrElseThrow(bookId);
    }

    private void validateDuplicatedIsbn(String isbn) {
        boolean exists = bookRepository.existsByIsbn(isbn);

        if(exists) throw new BookException(ErrorCode.DUPLICATED, "Already Exists Isbn");
    }
}
