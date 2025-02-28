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
    public ApiResponse<Void> create(@Valid @RequestBody CreateAuthorRequest request) {
        authorService.create(request);

        return ApiResponse.created(null);
    }

    @Override
    @PatchMapping("/authors/{authorId}")
    public ApiResponse<Void> update(
            @Valid @RequestBody UpdateAuthorRequest request, @PathVariable("authorId") Long authorId
    ) {
        authorService.update(request, authorId);

        return ApiResponse.ok(null);
    }

    @Override
    @DeleteMapping("/authors/{authorId}")
    public ApiResponse<Void> delete(@PathVariable("authorId") Long authorId) {
        authorService.delete(authorId);

        return ApiResponse.ok(null);
    }

    @Override
    @GetMapping("/authors")
    public ApiResponse<List<GetAuthorResponse>> readAuthors() {
        List<GetAuthorResponse> response = authorService.readAuthors();

        return ApiResponse.ok(response);
    }

    @Override
    @GetMapping("/authors/{authorId}")
    public ApiResponse<GetAuthorDetailResponse> readAuthorDetail(@PathVariable Long authorId) {
        GetAuthorDetailResponse response = authorService.readAuthorDetail(authorId);

        return ApiResponse.ok(response);
    }

}
