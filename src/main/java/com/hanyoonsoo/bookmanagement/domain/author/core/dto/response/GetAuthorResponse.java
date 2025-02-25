package com.hanyoonsoo.bookmanagement.domain.author.core.dto.response;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAuthorResponse {

    private Long authorId;

    private String name;

    private String email;

    public static GetAuthorResponse from(Author author){
        return GetAuthorResponse.builder()
                .authorId(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .build();
    }
}
