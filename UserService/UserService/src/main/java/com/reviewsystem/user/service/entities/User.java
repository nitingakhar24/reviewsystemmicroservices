package com.reviewsystem.user.service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    private String userId;
    @Column(length = 200)
    private String name;
    private String email;
    private String about;
    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
