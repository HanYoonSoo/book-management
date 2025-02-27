package com.hanyoonsoo.bookmanagement.domain.book.core.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class GetBookResponse {

    @Schema(description = "도서 Id", example = "1")
    private Long bookId;

    @Schema(description = "도서 타이틀", example = "도서 타이틀")
    private String title;

    @Schema(description = "도서 설명", example = "도서 설명")
    private String description;

    @Schema(description = "저자 이름", example = "홍길동")
    private String authorName;

    @Schema(description = "국제 표준 도서번호", example = "1234567890")
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "출판일", example = "2025-02-27")
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
