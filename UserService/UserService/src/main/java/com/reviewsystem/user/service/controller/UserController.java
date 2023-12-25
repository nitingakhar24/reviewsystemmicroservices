package com.reviewsystem.user.service.controller;

import com.reviewsystem.user.service.entities.User;
import com.reviewsystem.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    int retryCount = 1;
    //single user get
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    @Retry(name = "ratingHotelService" , fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        User fetchedUser = userService.getUser(userId);
        return ResponseEntity.ok(fetchedUser);
    }

    // creating fall back method for circuitbreaker

    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception exception) {
        //logger.info("Fallback method is executed because service is down :" + exception.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This is a dummy user because some service is down")
                .userId(userId).build();
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }

    // get all users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
