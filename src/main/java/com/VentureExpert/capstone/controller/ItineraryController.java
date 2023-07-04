package com.VentureExpert.capstone.controller;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.repositories.UserRepository;
import com.VentureExpert.capstone.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    void addItinerary(@RequestBody Map<String, String> json){
        itineraryService.addItinerary(json);
    }

    @GetMapping("/findByUser/{userId}")
    public List<Map<String, Object>> findItineraryByUser(@PathVariable Integer userId) {
        return itineraryService.findItineraryByUser(userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteItineraryById(@PathVariable Integer id){
        itineraryService.deleteItineraryById(id);
    }
}
