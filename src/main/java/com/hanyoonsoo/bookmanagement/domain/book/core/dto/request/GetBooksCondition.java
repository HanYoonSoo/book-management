package com.hanyoonsoo.bookmanagement.domain.book.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanyoonsoo.bookmanagement.domain.book.core.dto.SortCriteria;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetBooksCondition {

    private String title;

    private String isbn;

    private String authorName;

    private LocalDate startPublicationDate;

    private LocalDate endPublicationDate;

    private SortCriteria sortCriteria = SortCriteria.ID_DESC;
}
