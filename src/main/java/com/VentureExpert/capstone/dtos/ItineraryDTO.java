package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDTO {
    private Integer id;
    private User user;
    private Location location;
    private String title;
    private Date start;
    private Date end;

    public ItineraryDTO(Itinerary itinerary) {
        this.id = itinerary.getId();
        this.user = itinerary.getUser();
        this.location = itinerary.getLocation();
        this.title = itinerary.getTitle();
        this.start = itinerary.getStart();
        this.end = itinerary.getEnd();
    }
}

