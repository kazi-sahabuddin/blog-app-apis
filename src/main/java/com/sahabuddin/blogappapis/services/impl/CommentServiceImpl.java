package com.sahabuddin.blogappapis.services.impl;

import com.sahabuddin.blogappapis.entities.Comment;
import com.sahabuddin.blogappapis.entities.Post;
import com.sahabuddin.blogappapis.exceptions.ResourceNotFoundException;
import com.sahabuddin.blogappapis.payloads.CommentDto;
import com.sahabuddin.blogappapis.repositories.CommentRepository;
import com.sahabuddin.blogappapis.repositories.PostRepository;
import com.sahabuddin.blogappapis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "comment id", commentId));
        commentRepository.delete(comment);
    }
}
