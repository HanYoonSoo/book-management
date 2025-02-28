package com.hanyoonsoo.bookmanagement.domain.author.core.dto.response;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetAuthorResponse {

    @Schema(description = "저자 Id", example = "1")
    private Long authorId;

    @Schema(description = "저자 이름", example = "홍길동")
    private String name;

    @Schema(description = "저자 이메일", example = "test@example.com")
    private String email;

    public static GetAuthorResponse from(Author author){
        return GetAuthorResponse.builder()
                .authorId(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .build();
    }
}
