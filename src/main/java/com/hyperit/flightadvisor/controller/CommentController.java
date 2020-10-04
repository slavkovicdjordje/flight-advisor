package com.hyperit.flightadvisor.controller;

import com.hyperit.flightadvisor.bean.CommentDto;
import com.hyperit.flightadvisor.entity.Comment;
import com.hyperit.flightadvisor.mapstruct.CommentMapper;
import com.hyperit.flightadvisor.security.CustomUsernamePasswordAuthenticationToken;
import com.hyperit.flightadvisor.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
//@PreAuthorize("hasRole('ROLE_USER')")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody CommentDto commentDto, Principal principal) {

        Integer userId = ((CustomUsernamePasswordAuthenticationToken) principal).getUserId();

        Comment result = commentService.addComment(
                CommentMapper.INSTANCE.commentDtoToComment(commentDto), userId);

        return CommentMapper.INSTANCE.commentToCommentDto(result);
    }

    @PutMapping
    public CommentDto editComment(@RequestBody CommentDto commentDto, Principal principal) {

        Integer userId = ((CustomUsernamePasswordAuthenticationToken) principal).getUserId();

        Comment result =  commentService.editComment(
                CommentMapper.INSTANCE.commentDtoToComment(commentDto), userId);

        return CommentMapper.INSTANCE.commentToCommentDto(result);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer id, Principal principal) {

        Integer userId = ((CustomUsernamePasswordAuthenticationToken) principal).getUserId();
        commentService.deleteComment(id, userId);
    }

}
