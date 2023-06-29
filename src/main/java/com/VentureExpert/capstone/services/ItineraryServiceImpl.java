package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;

public class ItineraryServiceImpl implements ItineraryService{
    @Override
    public void addItinerary(Integer userId, LocationDTO locationDTO) {
        /**
         * add to location table first-return the id
         * then add to the itinerary table with userId == user_id and locationDTO.getId == location_id
         */
    }
}
