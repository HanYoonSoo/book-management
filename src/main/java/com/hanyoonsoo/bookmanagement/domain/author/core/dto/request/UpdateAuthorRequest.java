package com.hanyoonsoo.bookmanagement.domain.author.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAuthorRequest {

    @Schema(description = "저자 이름", example = "홍길동", nullable = true)
    private String name;

    @Schema(description = "저자 이메일", example = "abc@example.com", nullable = true)
    private String email;
}
