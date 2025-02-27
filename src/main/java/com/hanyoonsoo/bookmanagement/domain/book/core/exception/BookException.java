package com.hanyoonsoo.bookmanagement.domain.book.core.exception;

import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import com.hanyoonsoo.bookmanagement.global.common.exception.GlobalException;

public class BookException extends GlobalException {
    public BookException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
