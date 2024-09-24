package com.sahabuddin.blogappapis.services;

import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto getPost(Long postId);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> searchPosts(String searchTerm);

    List <PostDto> getAllPosts();

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    PostDto updatePost(PostDto postDto, Long postId);

    void deletePost(Long postId);
}
