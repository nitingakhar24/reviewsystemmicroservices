package com.reviewsystem.user.service.service.impl;

import com.reviewsystem.user.service.entities.Hotel;
import com.reviewsystem.user.service.entities.Rating;
import com.reviewsystem.user.service.entities.User;
import com.reviewsystem.user.service.exceptions.ResourceNotFoundException;
import com.reviewsystem.user.service.repositories.UserRepository;
import com.reviewsystem.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
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
        // Fetch rating of the above user from RATING-SERVICE
        //http://localhost:8083/ratings/users/7e0bf359-355b-4916-886b-4f25644cf687
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        logger.info("{}", ratings);
        List<Rating> ratingsList = ratings.stream().map(rating -> {
            // api call to hotel service to get the hotel
            //http://localhost:8082/hotels/c523a32d-56ee-4a4d-9434-d9c7fe823481
            ResponseEntity<Hotel> responseEntityWithHotel = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = responseEntityWithHotel.getBody();
            logger.info("Response status code", responseEntityWithHotel.getStatusCode());
            // set the hotel to rating
            rating.setHotel(hotel);
            // return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingsList);
        return user;
    }
}
