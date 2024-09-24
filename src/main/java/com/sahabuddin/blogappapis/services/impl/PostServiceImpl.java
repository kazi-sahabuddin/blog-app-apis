package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.Category;
import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.entities.User;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.PostDto;
import com.sahabuddin.blogappapis.repositories.CategoryRepository;
import com.sahabuddin.blogappapis.repositories.PostRepository;
import com.sahabuddin.blogappapis.repositories.UserRepository;
import com.sahabuddin.blogappapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDto getPost(Long postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        return List.of();
    }

    @Override
    public List<PostDto> searchPosts(String searchTerm) {
        return List.of();
    }

    @Override
    public List<PostDto> getAllPosts() {
        return List.of();
    }

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "user id", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "category id", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        return this.modelMapper.map(this.postRepository.save(post), PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }
}
