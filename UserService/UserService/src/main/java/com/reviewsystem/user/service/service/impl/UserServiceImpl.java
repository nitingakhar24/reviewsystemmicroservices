package com.reviewsystem.user.service.service.impl;

import com.reviewsystem.user.service.entities.Hotel;
import com.reviewsystem.user.service.entities.Rating;
import com.reviewsystem.user.service.entities.User;
import com.reviewsystem.user.service.exceptions.ResourceNotFoundException;
import com.reviewsystem.user.service.externalservices.HotelService;
import com.reviewsystem.user.service.externalservices.RatingService;
import com.reviewsystem.user.service.repositories.UserRepository;
import com.reviewsystem.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RatingService ratingService;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        // Get user from DB with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id not found " + userId));
        //Get ratings for a user
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        logger.info("{}", ratings);
        //For each ratings get which hotel is rated
        List<Rating> ratingsForHotel = getRatingsForEachHotel(ratings);
        user.setRatings(ratingsForHotel);
        return user;
    }

    private List<Rating> getRatingsForEachHotel(List<Rating> ratings) {
        return ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
    }
}
