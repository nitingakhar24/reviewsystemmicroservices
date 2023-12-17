package com.reviewsystem.hotel.service.services;

import com.reviewsystem.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {

    /**
     * Saves a hotel
     * @param hotel
     * @return
     */
    Hotel create(Hotel hotel);

    /**
     * Gets a list of hotels
     * @return
     */
    List<Hotel> getAllHotels();

    /**
     * Get Hotel by ID
     * @param id
     * @return
     */
    Hotel getHotel(String id);
}
