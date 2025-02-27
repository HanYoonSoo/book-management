package com.hanyoonsoo.bookmanagement.domain.book.core.dto.response;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class GetBookDetailResponse extends GetBookResponse{

    @Schema(description = "저자 이메일", example = "author@example.com")
    private String authorEmail;

    public static GetBookDetailResponse from(Book book) {
        return GetBookDetailResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authorName(book.getAuthor().getName())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .authorEmail(book.getAuthor().getEmail())
                .build();
    }
}
