package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDTO {
    private Integer itineraryId;
    private User user;

    public ItineraryDTO(Itinerary itinerary) {
        this.itineraryId = itinerary.getItineraryId();
        this.user = itinerary.getUser();
    }
}
