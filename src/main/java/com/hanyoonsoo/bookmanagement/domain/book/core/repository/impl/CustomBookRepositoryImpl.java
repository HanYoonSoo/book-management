package com.hanyoonsoo.bookmanagement.domain.book.core.repository.impl;

import com.hanyoonsoo.bookmanagement.domain.book.core.dto.SortCriteria;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.request.GetBooksCondition;
import com.hanyoonsoo.bookmanagement.domain.book.core.entity.Book;
import com.hanyoonsoo.bookmanagement.domain.book.core.repository.CustomBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static com.hanyoonsoo.bookmanagement.domain.book.core.entity.QBook.book;

@Repository
@RequiredArgsConstructor
public class CustomBookRepositoryImpl implements CustomBookRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Book> findPagedBooksByCondition(GetBooksCondition condition, Pageable pageable) {
        List<Book> books =  jpaQueryFactory.selectFrom(book)
                .where(
                        bookTitleContains(condition.getTitle()),
                        bookIsbnContains(condition.getIsbn()),
                        bookAuthorNameContains(condition.getAuthorName()),
                        bookPublicationDateBetween(condition.getStartPublicationDate(), condition.getEndPublicationDate()),
                        createSortPredicate(condition)
                )
                .orderBy(createOrderSpecifier(condition.getSortCriteria()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(book.count())
                .from(book)
                .where(
                        bookTitleContains(condition.getTitle()),
                        bookIsbnContains(condition.getIsbn()),
                        bookAuthorNameContains(condition.getAuthorName()),
                        bookPublicationDateBetween(condition.getStartPublicationDate(), condition.getEndPublicationDate()),
                        createSortPredicate(condition)
                );

        return PageableExecutionUtils.getPage(books, pageable, countQuery::fetchOne);
    }

    private BooleanBuilder bookTitleContains(String title) {
        return this.nullSafeBuilder(() -> book.title.contains(title));
    }

    private BooleanBuilder bookIsbnContains(String isbn) {
        return this.nullSafeBuilder(() -> book.isbn.contains(isbn));
    }

    private BooleanBuilder bookAuthorNameContains(String authorName) {
        return this.nullSafeBuilder(() -> book.author.name.contains(authorName));
    }

    private BooleanBuilder bookPublicationDateBetween(LocalDate startPublicationDate, LocalDate endPublicationDate) {
        return this.nullSafeBuilder(() -> book.publicationDate.between(startPublicationDate, endPublicationDate));
    }

    private BooleanBuilder createSortPredicate(GetBooksCondition condition) {
        return switch (condition.getSortCriteria()) {
            case PUBLICATION_DATE_ASC, PUBLICATION_DATE_DESC
                    -> this.nullSafeBuilder(book.publicationDate::isNotNull);
            default -> null;
        };
    }

    private OrderSpecifier<?>[] createOrderSpecifier(SortCriteria sortCriteria) {
        return switch (sortCriteria) {
            case TITLE_ASC -> new OrderSpecifier[] {
                    new OrderSpecifier<>(Order.ASC, book.title)
            };
            case TITLE_DESC -> new OrderSpecifier[] {
                    new OrderSpecifier<>(Order.DESC, book.title)
            };
            case PUBLICATION_DATE_ASC -> new OrderSpecifier[] {
                    new OrderSpecifier<>(Order.ASC, book.publicationDate)
            };
            case PUBLICATION_DATE_DESC -> new OrderSpecifier[] {
                    new OrderSpecifier<>(Order.DESC, book.publicationDate)
            };
            default -> new OrderSpecifier[]{
                    new OrderSpecifier<>(Order.DESC, book.id)
            };
        };
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
