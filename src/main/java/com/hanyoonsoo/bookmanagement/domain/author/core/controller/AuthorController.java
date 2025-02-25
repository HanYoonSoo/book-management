package com.hanyoonsoo.bookmanagement.domain.author.core.controller;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Author", description = "<b>[Author]</b> API")
public interface AuthorController {

    @Operation(
            summary = "Author 생성",
            description = "Author 생성 API, 이메일이 중복되지 않았다면 Author 생성에 성공합니다. " +
                    "이미 존재하는 이메일일 경우 생성에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "Author 생성 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409", description = "이미 존재하는 이메일로 인해 Author 생성 실패"
    )
    ApiResponse<Void> create(CreateAuthorRequest request);
}
