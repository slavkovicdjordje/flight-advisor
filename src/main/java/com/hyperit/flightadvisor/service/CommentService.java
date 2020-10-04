package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.Comment;

public interface CommentService {

    /**
     * Save new comment.
     *
     * @param comment  Comment to be saved.
     * @param userId Username of user which is adding comment.
     * @return saved comment
     */
    Comment addComment(Comment comment, Integer userId);

    /**
     * Edit existing comment.
     *
     * @param comment  Comment to be updated.
     * @param userId Id of user which is editing comment.
     * @return saved comment
     */
    Comment editComment(Comment comment, Integer userId);

    /**
     * Delete comment by id.
     *
     * @param id     Id of comment.
     * @param userId Id of user which is deleting comment.
     */
    void deleteComment(Integer id, Integer userId);
}
