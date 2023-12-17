package com.reviewsystem.user.service.service;

import com.reviewsystem.user.service.entities.User;

import java.util.List;

/*
    Define all user related operations
 */
public interface UserService {

    /**
     * Creates a user
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * Get All Users
     * @return
     */
    List<User> getAllUsers();

    /**
     * Get user by userId
     * @param userId
     * @return
     */
    User getUser(String userId);
}
