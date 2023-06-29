package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;

public interface ItineraryService {
    void addItinerary(Integer userId, LocationDTO locationDTO);
}
