package com.reviewsystem.rating.service.controller;

import com.reviewsystem.rating.service.entities.Rating;
import com.reviewsystem.rating.service.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }


    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> ratings = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

}
