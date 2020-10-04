package com.hyperit.flightadvisor.service.impl;

import com.hyperit.flightadvisor.exception.BadRequestException;
import com.hyperit.flightadvisor.repository.CommentRepository;
import com.hyperit.flightadvisor.entity.Comment;
import com.hyperit.flightadvisor.entity.User;
import com.hyperit.flightadvisor.service.CommentService;
import com.hyperit.flightadvisor.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Comment addComment(Comment comment, Integer userId) {
        log.debug(format("Adding comment for city %s", comment.getCity().getId()));

        User user = userService.findById(userId);
        comment.setUser(user);
        comment.setDateAdded(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public Comment editComment(Comment comment, Integer userId) {
        log.debug(format("Editing comment with id %s", comment.getId()));

        Comment existingComment = validateComment(comment.getId(), userId);
        existingComment.setComment(comment.getComment());
        existingComment.setDateModified(new Date());

        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Integer id, Integer userId) {
        log.debug(format("Deleting comment with id %s", id));

        validateComment(id, userId);
        commentRepository.deleteById(id);
    }

    private Comment validateComment(Integer id, Integer userId) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no comment with id " + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new BadRequestException("Comment is not created by user with id " + userId);
        }

        return comment;
    }
}
