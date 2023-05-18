package com.example.travelApp.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "trip_name", nullable = true)
    private String tripName;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "destination")
    private String destination;

    @Column(name = "trip_type")
    private String tripType;

    @Column(name = "price")
    private Float price;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "photo_uri")
    private String photoUri;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "is_favourite")
    private Boolean isFavourite;
}
