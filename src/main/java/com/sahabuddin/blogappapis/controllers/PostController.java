package com.sahabuddin.blogappapis.controllers;

import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.payloads.PostDto;
import com.sahabuddin.blogappapis.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(this.postService.getPostsByUser(userId));
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(this.postService.getPostsByCategory(categoryId));
    }


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Long userId,
                                              @PathVariable Long categoryId) {
        return new ResponseEntity<>(this.postService.createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

}
