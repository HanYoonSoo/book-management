package com.hanyoonsoo.bookmanagement.domain.author.core.entity;

import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@SQLDelete(sql =
    """
        UPDATE author 
        SET deleted_at = CURRENT_TIMESTAMP, email = CONCAT('deleted_', random_uuid(), '_', email) 
        WHERE id = ?
    """
)
@SQLRestriction("deleted_at is null")
@Table(name = "author")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 200, unique = true)
    private String email;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public static Author of(String name, String email) {
        return Author.builder()
                .name(name)
                .email(email)
                .build();
    }

    public void modifyName(String name) {
        this.name = name;
    }

    public void modifyEmail(String email) {
        this.email = email;
    }
}
