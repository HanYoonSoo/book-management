package com.hanyoonsoo.bookmanagement.domain.book.core.controller;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.CreateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.UpdateBookRequest;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookDetailResponse;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.response.GetBookResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.ApiResponse;
import com.hanyoonsoo.bookmanagement.global.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book", description = "<b>[Book]</b> API")
public interface BookController {

    @Operation(
            summary = "Book 생성",
            description = "Book 생성 API, isbn 중복되지 않았다면 Book 생성에 성공합니다. " +
                    "존재하지 않는 Author Id일 경우 404 에러를 반환합니다. " +
                    "이미 존재하는 isbn일 경우 생성에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201", description = "Book 생성 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Author Id일 경우 Book 생성 실패"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409", description = "이미 존재하는 isbn으로 인해 Book 생성 실패"
                    )
            }
    )
    ApiResponse<Void> createBook(CreateBookRequest request);

    @Operation(
            summary = "Book 수정",
            description = "Book 수정 API, isbn 중복되지 않았다면 Book 수정에 성공합니다. " +
                    "존재하지 않는 Book Id일 경우 404 에러를 반환합니다. " +
                    "이미 존재하는 isbn일 경우 수정에 실패하고 409 에러를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Book 수정 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Book Id일 경우 Book 수정 실패"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409", description = "이미 존재하는 isbn으로 인해 Book 수정 실패"
                    )
            }
    )
    ApiResponse<Void> updateBook(UpdateBookRequest request, Long bookId);

    @Operation(
            summary = "Book 삭제",
            description = "Book 삭제 API, 존재하는 Book Id의 경우 Book 삭제에 성공합니다. " +
                    "존재하지 않는 Book Id일 경우 404 에러를 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Book 삭제 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Book Id일 경우 Book 삭제 실패"
                    )
            }
    )
    ApiResponse<Void> deleteBook(Long bookId);

    @Operation(
            summary = "Book 목록 조회",
            description = "Book 목록 조회 API, 타이틀, isbn, 저자 이름, 출판일 범위로 필터링하여 페이지네이션 값을 반환합니다." +
                    "정렬 가능 조건으로는 ID 최신순(기본값), 제목순(오름차순, 내림차순), 출판일순(오름차순, 내림차순)<br><br> " +
                    "ENUM 값: ID 최신순 - ID_DESC, 제목순 - TITLE_ASC, TITLE_DESC, " +
                    "출판일순 - PUBLICATION_DATE_ASC, PUBLICATION_DATE_DESC"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "Book 목록 조회 성공"
    )
    ApiResponse<PageResponse<GetBookResponse>> getBooks(GetBooksCondition condition, int pageNo);

    @Operation(
            summary = "Book 상세 조회",
            description = "Book 상세 조회 API, Book의 상세 정보를 반환합니다." +
                    "존재하지 않는 Book Id일 경우 404 에러를 반환합니다. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "Book 목록 조회 성공"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404", description = "존재하지 않는 Book Id일 경우 Book 상세 조회 실패"
                    )
            }
    )
    ApiResponse<GetBookDetailResponse> getBookDetails(Long bookId);
}
