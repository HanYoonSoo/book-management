package com.hanyoonsoo.bookmanagement.domain.author.core.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateAuthorRequest {

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;
}
