package com.hanyoonsoo.bookmanagement.domain.author.core.controller;

import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.CreateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.request.UpdateAuthorRequest;
import com.hanyoonsoo.bookmanagement.domain.author.core.dto.response.GetAuthorResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Author", description = "<b>[Author]</b> API")
public interface AuthorController {

    @Operation(
            summary = "Author 생성",
            description = "Author 생성 API, 이메일이 중복되지 않았다면 Author 생성에 성공합니다. " +
                    "이미 존재하는 이메일일 경우 생성에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201", description = "Author 생성 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409", description = "이미 존재하는 이메일로 인해 Author 생성 실패"
                    )
            }
    )
    ApiResponse<Void> create(CreateAuthorRequest request);

    @Operation(
            summary = "Author 수정",
            description = "Author 수정 API, 이메일이 중복되지 않았다면 Author 수정에 성공합니다. " +
                    "name 필드, email 필드에 데이터 입력 시 수정 작업이 수행됩니다. " +
                    "존재하지 않는 Author Id일 경우 404 에러를 반환합니다. " +
                    "이미 존재하는 이메일일 경우 수정에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Author 수정 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Author Id로 접근 시 수정 실패"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409", description = "이미 존재하는 이메일로 인해 Author 수정 실패"
                    )
            }
    )
    ApiResponse<Void> update(UpdateAuthorRequest request, Long authorId);

    @Operation(
            summary = "Author 삭제",
            description = "Author 삭제 API, 일치하는 Author Id가 존재한다면 Author 삭제에 성공합니다. " +
                    "존재하지 않는 Author Id일 경우 404 에러를 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Author 삭제 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Author Id로 접근 시 삭제 실패"
                    )
            }
    )
    ApiResponse<Void> delete(Long authorId);

    @Operation(
            summary = "Author 목록 조회",
            description = "Author 목록 조회 API, 모든 저자 목록을 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Author 목록 조회 성공"
    )
    ApiResponse<List<GetAuthorResponse>> readAuthors();

    @Operation(
            summary = "Author 상세 정보 조회",
            description = "Author 상세 정보 조회 API, 저자의 상세 정보를 반환합니다. " +
                    "존재하지 않는 Author Id일 경우 404 에러를 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Author 상세 정보 조회 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Author Id로 접근 시 상세 정보 조회 실패"
                    )
            }
    )
    ApiResponse<GetAuthorResponse> readAuthorDetail(Long authorId);

}
