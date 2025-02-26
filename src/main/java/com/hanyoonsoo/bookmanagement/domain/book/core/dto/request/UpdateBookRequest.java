package com.hanyoonsoo.bookmanagement.domain.book.core.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBookRequest {

    private String title;

    private String description;

    /**
     * ISBN-10 규칙을 적용:
     * - 10자리 숫자 형식
     * - 국가/언어 식별 번호: 첫 2자리 (10 ~ 90)
     * - 출판사 식별 번호: 3 ~ 6자리
     * - 책 식별 번호: 7 ~ 9자리
     * - 체크 디지트: 마지막 자리 (항상 0)
     */
    @Pattern(
            regexp = "^(1[0-9]|[2-8][0-9]|90)[0-9]{3,4}[0-9]{1,3}0$"
    )
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}
