package com.hanyoonsoo.bookmanagement.domain.author.core.controller.impl;

import com.hanyoonsoo.bookmanagement.domain.author.core.controller.AuthorController;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.service.AuthorService;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;

    @Override
    @RequestMapping("/authors")
    public ApiResponse<Void> create(@Valid @RequestBody CreateAuthorRequest request) {
        authorService.create(request);

        return ApiResponse.created(null);
    }
}
