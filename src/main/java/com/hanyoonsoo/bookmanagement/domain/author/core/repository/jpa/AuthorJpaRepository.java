package com.hanyoonsoo.bookmanagement.domain.author.core.repository.jpa;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {

    boolean existsByEmail(String email);
}
