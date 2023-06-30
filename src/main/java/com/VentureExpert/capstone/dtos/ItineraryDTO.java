package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDTO {
    private Integer id;
    private User user;
    private String title;
    private Date start;
    private Time end;

    public ItineraryDTO(Itinerary itinerary) {
        this.id = itinerary.getId();
        this.title = itinerary.getTitle();
        this.start = itinerary.getStart();
        this.end = itinerary.getEnd();
        this.user = itinerary.getUser();
    }
}
