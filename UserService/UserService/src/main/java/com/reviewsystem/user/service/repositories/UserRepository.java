package com.reviewsystem.user.service.repositories;

import com.reviewsystem.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
