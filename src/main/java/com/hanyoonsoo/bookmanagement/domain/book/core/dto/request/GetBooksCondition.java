package com.hanyoonsoo.bookmanagement.domain.book.core.dto.request;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.SortCriteria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetBooksCondition {

    @Schema(description = "도서 타이틀", example = "도서 타이틀", nullable = true)
    private String title;

    @Schema(description = "국제 표준 도서번호", example = "1234567890", nullable = true)
    private String isbn;

    @Schema(description = "저자 이름", example = "홍길동", nullable = true)
    private String authorName;

    @Schema(description = "검색 시작 출판일", example = "2025-02-23", nullable = true)
    private LocalDate startPublicationDate;

    @Schema(description = "검색 종료 출판일", example = "2025-02-28", nullable = true)
    private LocalDate endPublicationDate;

    @Schema(description = "도서 목록 조회 정렬 조건", example = "ID_DESC, TITLE_ASC/DESC, PUBLICATION_DATE_ASC/DESC", nullable = true)
    private SortCriteria sortCriteria = SortCriteria.ID_DESC;
}
