package com.hanyoonsoo.bookmanagement.domain.book.core.controller.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.controller.BookController;
import com.hanyoonsoo.bookmanagement.domain.book.core.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;
}
