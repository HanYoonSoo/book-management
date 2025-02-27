package com.hanyoonsoo.bookmanagement.domain.book.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortCriteria {
    ID_DESC,
    TITLE_ASC,
    TITLE_DESC,
    PUBLICATION_DATE_ASC,
    PUBLICATION_DATE_DESC;
}
