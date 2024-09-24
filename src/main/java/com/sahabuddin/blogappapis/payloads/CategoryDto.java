package com.sahabuddin.blogappapis.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Long categoryId;

    @NotEmpty
    @Size(min = 4, message = "Minimum size of 4 characters")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10, message = "Minimum size of 10 characters")
    private String categoryDescription;
}
