package com.hanyoonsoo.bookmanagement.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_VALID(HttpStatus.BAD_REQUEST, "Not Valid"),
    DUPLICATED(HttpStatus.CONFLICT, "Duplicated"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

    private final HttpStatus httpStatus;
    private final String message;
}
