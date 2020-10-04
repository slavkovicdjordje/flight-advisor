package com.hyperit.flightadvisor.service;

import com.hyperit.flightadvisor.entity.User;

/**
 * Service for user related operations.
 */
public interface UserService {

    /**
     * Save new user.
     *
     * @param user {@link User}
     * @return saved user
     */
    User saveUser(User user);

    /**
     * Find user by ID.
     *
     * @param id Id of user.
     * @return user
     */
    User findById(Integer id);

}
