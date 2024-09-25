package com.sahabuddin.blogappapis.payloads;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Long postId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String imageName;

    private CategoryDto category;

    private UserDto user;

}
