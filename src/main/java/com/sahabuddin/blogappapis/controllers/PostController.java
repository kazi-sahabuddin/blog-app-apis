package com.sahabuddin.blogappapis.controllers;

import com.sahabuddin.blogappapis.config.AppConstants;
import com.sahabuddin.blogappapis.payloads.ApiResponse;
import com.sahabuddin.blogappapis.payloads.PostDto;
import com.sahabuddin.blogappapis.payloads.PostResponse;
import com.sahabuddin.blogappapis.services.FileService;
import com.sahabuddin.blogappapis.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;

    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
        return ResponseEntity.ok(postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(this.postService.getPost(postId));
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

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto, postId));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam(value = "q") String q){
        return ResponseEntity.ok(postService.searchPosts(q));
    }

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getImage(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@PathVariable Long postId, @RequestParam("file") MultipartFile file) throws IOException {
        PostDto postDto = postService.getPost(postId);
        String fileName = fileService.uploadImage(path, file);
        postDto.setImageName(fileName);
        return ResponseEntity.ok(postService.updatePost(postDto, postId));
    }

}
