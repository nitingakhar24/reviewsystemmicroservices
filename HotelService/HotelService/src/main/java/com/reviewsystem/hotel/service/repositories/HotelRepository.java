package com.reviewsystem.hotel.service.repositories;

import com.reviewsystem.hotel.service.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
