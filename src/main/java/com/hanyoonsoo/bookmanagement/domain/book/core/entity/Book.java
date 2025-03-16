package com.hanyoonsoo.bookmanagement.domain.book.core.entity;

import com.hanyoonsoo.bookmanagement.domain.author.core.entity.Author;
import com.hanyoonsoo.bookmanagement.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@SQLDelete(sql =
    """
        UPDATE book 
        SET deleted_at = CURRENT_TIMESTAMP, isbn = CONCAT('deleted_', random_uuid(), '_', isbn) 
        WHERE id = ?
    """
)
@SQLRestriction("deleted_at is null")
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "isbn", nullable = false, length = 60, unique = true)
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public static Book of(
            String title,
            String description,
            String isbn,
            LocalDate publicationDate,
            Author author
    ) {
        return Book.builder()
                .title(title)
                .isbn(isbn)
                .publicationDate(publicationDate)
                .description(description)
                .author(author)
                .build();
    }

    public void modifyTitle(String title) {
        this.title = title;
    }

    public void modifyIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void modifyDescription(String description) {
        this.description = description;
    }

    public void modifyPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
