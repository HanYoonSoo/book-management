package com.hanyoonsoo.bookmanagement.domain.author.core.dto.response;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class GetAuthorDetailResponse extends GetAuthorResponse{

    @ArraySchema(schema = @Schema(implementation = GetBookResponse.class))
    @Schema(description = "저자 연관 도서")
    private List<GetBookResponse> books;

    public static GetAuthorDetailResponse from(Author author){
        return GetAuthorDetailResponse.builder()
                .authorId(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .books(author.getBooks() != null ?
                        author.getBooks().stream().map(GetBookResponse::from).toList()
                        : null
                )
                .build();
    }
}
