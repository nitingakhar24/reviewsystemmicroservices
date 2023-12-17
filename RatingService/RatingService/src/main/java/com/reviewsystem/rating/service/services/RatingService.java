package com.reviewsystem.rating.service.services;

import com.reviewsystem.rating.service.entities.Rating;

import java.util.List;

public interface RatingService {
    /**
     * Create a rating
     *
     * @param rating
     * @return
     */
    Rating createRating(Rating rating);

    /**
     * Get all the ratings
     *
     * @return
     */
    List<Rating> getAllRatings();

    /**
     * Get all ratings by a UserId
     *
     * @return
     */
    List<Rating> getRatingsByUserId(String userId);

    /**
     * Get all ratings by a HotelId
     *
     * @param hotelId
     * @return
     */
    List<Rating> getRatingsByHotelId(String hotelId);

}
