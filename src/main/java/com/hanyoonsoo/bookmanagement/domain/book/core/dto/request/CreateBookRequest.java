package com.hanyoonsoo.bookmanagement.domain.book.core.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest {

    @NotBlank
    @Schema(description = "도서 제목", example = "도서 타이틀")
    private String title;

    @Schema(description = "도서 설명", example = "도서에 대한 설명", nullable = true)
    private String description;

    /**
     * ISBN-10 규칙을 적용:
     * - 10자리 숫자 형식
     * - 국가/언어 식별 번호: 첫 2자리 (10 ~ 90)
     * - 출판사 식별 번호: 3 ~ 6자리
     * - 책 식별 번호: 7 ~ 9자리
     * - 체크 디지트: 마지막 자리 (항상 0)
     */
    @NotBlank
    @Pattern(
            regexp = "^(1[0-9]|[2-8][0-9]|90)[0-9]{3,4}[0-9]{1,3}0$"
    )
    @Schema(description = "국제 표준 도서번호", example = "1234567890")
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "도서 출판일", example = "2025-02-26", nullable = true)
    private LocalDate publicationDate;

    @NotNull
    @Schema(description = "도서 저자", example = "1")
    private Long authorId;
}
