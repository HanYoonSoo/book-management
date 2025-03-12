package com.hanyoonsoo.bookmanagement.domain.author.core.controller.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.controller.AuthorController;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    @Override
    @PostMapping("/authors")
    public ApiResponse<Void> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        authorService.createAuthor(request);

        return ApiResponse.created(null);
    }

    @Override
    @PatchMapping("/authors/{authorId}")
    public ApiResponse<Void> updateAuthor(
            @Valid @RequestBody UpdateAuthorRequest request, @PathVariable("authorId") Long authorId
    ) {
        authorService.updateAuthor(request, authorId);

        return ApiResponse.ok(null);
    }

    @Override
    @DeleteMapping("/authors/{authorId}")
    public ApiResponse<Void> deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthor(authorId);

        return ApiResponse.ok(null);
    }

    @Override
    @GetMapping("/authors")
    public ApiResponse<List<GetAuthorResponse>> getAuthors() {
        List<GetAuthorResponse> response = authorService.getAuthors();

        return ApiResponse.ok(response);
    }

    @Override
    @GetMapping("/authors/{authorId}")
    public ApiResponse<GetAuthorDetailResponse> getAuthorDetails(@PathVariable("authorId") Long authorId) {
        GetAuthorDetailResponse response = authorService.getAuthorDetails(authorId);

        return ApiResponse.ok(response);
    }

}
