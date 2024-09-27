package com.sahabuddin.blogappapis.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDto> contents;

    private int pageNumber;

    private int pageSize;

    private int totalElementsCount;

    private int totalPages;

    private boolean lastPage;
}
