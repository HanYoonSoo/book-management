package com.hanyoonsoo.bookmanagement.domain.book.fake;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.SortCriteria;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.exception.BookException;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.BookRepository;
import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryBookRepository implements BookRepository {

    private final Map<Long, Book> books = new HashMap<>();
    public Long bookId = 1L;

    @Override
    public void save(Book book) {
        books.put(bookId++, book);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.values().stream()
                .anyMatch(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public Book findByIdOrElseThrow(Long bookId) {
        return Optional.ofNullable(books.get(bookId))
                .orElseThrow(() -> new BookException(ErrorCode.NOT_FOUND, "Not Found Book"));
    }

    @Override
    public void delete(Book book) {
        books.entrySet().removeIf(entry ->
                entry.getValue().getId().equals(book.getId())
        );
    }

    @Override
    public Page<Book> findPagedBooksByCondition(GetBooksCondition condition, Pageable pageable) {
        List<Book> filteredBooks;

        if(condition != null) {
            filteredBooks = books.values().stream()
                    .filter(book -> isNullOrBlank(condition.getTitle()) ||
                            (book.getTitle() != null && book.getTitle().contains(condition.getTitle())))
                    .filter(book -> isNullOrBlank(condition.getIsbn())
                            || (book.getIsbn() != null && book.getIsbn().contains(condition.getIsbn())))
                    .filter(book -> isNullOrBlank(condition.getAuthorName())
                            || (book.getAuthor().getName() != null && book.getAuthor().getName().contains(condition.getAuthorName())))
                    .filter(book -> isDateInRange(book.getPublicationDate(), condition.getStartPublicationDate(), condition.getEndPublicationDate()))
                    .collect(Collectors.toList());

            Comparator<Book> comparator = getComparator(condition.getSortCriteria());
            if (comparator != null) {
                filteredBooks.sort(comparator);
            }
        } else {
            filteredBooks = new ArrayList<>(books.values());
        }

        int total = filteredBooks.size();
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        List<Book> pagedList = offset >= total
                ? Collections.emptyList()
                : filteredBooks.subList(offset, Math.min(offset + pageSize, total));

        return new PageImpl<>(pagedList, pageable, total);
    }

    @Override
    public void deleteByAuthorId(Long authorId) {
        books.entrySet().removeIf(entry ->
                entry.getValue().getAuthor().getId().equals(authorId)
        );
    }

    private boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    private boolean isDateInRange(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null) return false;
        if (start != null && date.isBefore(start)) return false;
        if (end != null && date.isAfter(end)) return false;
        return true;
    }

    private Comparator<Book> getComparator(SortCriteria sortCriteria) {
        return switch (sortCriteria) {
            case TITLE_ASC -> Comparator.comparing(book ->
                    book.getTitle() != null ? book.getTitle().toLowerCase() : "");
            case TITLE_DESC -> Comparator.comparing((Book book) ->
                    book.getTitle() != null ? book.getTitle().toLowerCase() : "").reversed();
            case PUBLICATION_DATE_ASC -> Comparator.comparing(Book::getPublicationDate, Comparator.nullsLast(LocalDate::compareTo));
            case PUBLICATION_DATE_DESC -> Comparator.comparing(Book::getPublicationDate, Comparator.nullsLast(LocalDate::compareTo)).reversed();
            case ID_DESC -> Comparator.comparing(Book::getId).reversed();
        };
    }
}
