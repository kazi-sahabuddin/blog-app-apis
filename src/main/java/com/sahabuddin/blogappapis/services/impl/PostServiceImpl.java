package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.Category;
import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.entities.User;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.PostDto;
import com.sahabuddin.blogappapis.payloads.PostResponse;
import com.sahabuddin.blogappapis.repositories.CategoryRepository;
import com.sahabuddin.blogappapis.repositories.PostRepository;
import com.sahabuddin.blogappapis.repositories.UserRepository;
import com.sahabuddin.blogappapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map( post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));
        return this.postRepository.findByCategory(category).stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "user id", userId));
        return this.postRepository.findByUser(user).stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String searchTerm) {
        return List.of();
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> allPosts = postRepository.findAll(pageable);
        List<Post> posts = allPosts.getContent();
        List<PostDto> postDtoList = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
                .toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContents(postDtoList);
        postResponse.setPageNumber(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setTotalElementsCount(allPosts.getNumberOfElements());
        postResponse.setLastPage(allPosts.isLast());

        return postResponse;
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
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        this.postRepository.delete(post);
    }
}
