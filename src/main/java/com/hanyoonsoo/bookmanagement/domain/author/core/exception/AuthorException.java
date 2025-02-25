package com.hanyoonsoo.bookmanagement.domain.author.core.exception;

import com.hanyoonsoo.bookmanagement.global.common.enums.ErrorCode;
import com.hanyoonsoo.bookmanagement.global.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class AuthorException extends GlobalException {
    public AuthorException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
