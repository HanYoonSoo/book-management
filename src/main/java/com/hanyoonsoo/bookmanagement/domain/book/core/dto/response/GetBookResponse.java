package com.hanyoonsoo.bookmanagement.domain.book.core.dto.response;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetBookResponse {

    private Long bookId;

    private String title;

    private String description;

    private String authorName;

    private String isbn;

    private LocalDate publicationDate;

    public static GetBookResponse from(Book book){
        return GetBookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authorName(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .build();
    }
}
