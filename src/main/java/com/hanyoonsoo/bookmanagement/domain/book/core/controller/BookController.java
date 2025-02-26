package com.hanyoonsoo.bookmanagement.domain.book.core.controller;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;

public interface BookController {

    @Operation(
            summary = "Book 생성",
            description = "Book 생성 API, isbn 중복되지 않았다면 Book 생성에 성공합니다. " +
                    "존재하지 않는 Author Id일 경우 404 에러를 반환합니다. " +
                    "이미 존재하는 isbn일 경우 생성에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "Book 생성 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "존재하지 않는 Author Id일 경우 Book 생성 실패"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409", description = "이미 존재하는 isbn으로 인해 Book 생성 실패"
    )
    ApiResponse<Void> create(CreateBookRequest request);

    @Operation(
            summary = "Book 수정",
            description = "Book 수정 API, isbn 중복되지 않았다면 Book 수정에 성공합니다. " +
                    "존재하지 않는 Book Id일 경우 404 에러를 반환합니다. " +
                    "이미 존재하는 isbn일 경우 수정에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Book 수정 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "존재하지 않는 Book Id일 경우 Book 수정 실패"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409", description = "이미 존재하는 isbn으로 인해 Book 수정 실패"
    )
    ApiResponse<Void> update(UpdateBookRequest request, Long bookId);

    @Operation(
            summary = "Book 삭제",
            description = "Book 삭제 API, 존재하는 Book Id의 경우 Book 삭제에 성공합니다. " +
                    "존재하지 않는 Book Id일 경우 404 에러를 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Book 삭제 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "존재하지 않는 Book Id일 경우 Book 삭제 실패"
    )
    ApiResponse<Void> delete(Long bookId);
}
