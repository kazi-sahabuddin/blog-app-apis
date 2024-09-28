package com.sahabuddin.blogappapis.payloads;


import com.sahabuddin.blogappapis.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    private Set<CommentDto> comments = new HashSet<>();

}
