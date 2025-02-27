package com.hanyoonsoo.bookmanagement.domain.author.fixture;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;

public class AuthorFixture {

    public static Author createAuthor(){
        return Author.of("홍길동", "test@example.com");
    }
}
