package com.reviewsystem.hotel.service.controllers;

import com.reviewsystem.hotel.service.entities.Hotel;
import com.reviewsystem.hotel.service.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.create(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @GetMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('SCOPE_internal')")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId) {
        Hotel fetchedHotel = hotelService.getHotel(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedHotel);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }
}
