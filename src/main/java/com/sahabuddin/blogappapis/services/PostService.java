package com.sahabuddin.blogappapis.services;

import com.sahabuddin.blogappapis.payloads.PostDto;
import com.sahabuddin.blogappapis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto getPost(Long postId);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> searchPosts(String searchTerm);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    PostDto updatePost(PostDto postDto, Long postId);

    void deletePost(Long postId);
}
