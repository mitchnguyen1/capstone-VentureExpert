package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.entities.Itinerary;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ItineraryService {
    void addItinerary(Map<String, String> json);

    List<Map<String, Object>> findItineraryByUser(Integer userId);

    void deleteItineraryById(Integer Id);
    void updateItinerary(Map<String, String> json);



}
