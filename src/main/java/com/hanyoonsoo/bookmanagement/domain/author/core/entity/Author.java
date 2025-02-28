package com.hanyoonsoo.bookmanagement.domain.author.core.entity;

import com.hanyoonsoo.bookmanagement.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@SQLDelete(sql = "UPDATE author SET deleted_at = CURRENT_TIMESTAMP, email = CONCAT('deleted_', UUID()) WHERE id = ?")
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
