package com.hanyoonsoo.bookmanagement.domain.author.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateAuthorRequest {

    @Schema(description = "저자 이름", example = "홍길동")
    @NotEmpty
    private String name;

    @Schema(description = "저자 이메일", example = "abc@example.com")
    @Email
    @NotEmpty
    private String email;
}
