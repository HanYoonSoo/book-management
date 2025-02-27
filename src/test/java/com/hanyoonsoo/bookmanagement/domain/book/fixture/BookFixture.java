package com.hanyoonsoo.bookmanagement.domain.book.fixture;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;

import java.time.LocalDate;

public class BookFixture {

    public static Book createBook(Author author){
        return Book.of(
                "테스트 타이틀",
                "테스트 설명",
                "1234567890",
                LocalDate.now(),
                author
        );
    }
}
