package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.User;
import com.VentureExpert.capstone.repositories.ItineraryRepository;
import com.VentureExpert.capstone.repositories.LocationRepository;
import com.VentureExpert.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class ItineraryServiceImpl implements ItineraryService {


    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addItinerary(Map<String, String> json) {
        /**
         {
         "userId": 1,
         "title": "San Fran",
         "start": "2023-07-01",
         "end": "2023-07-05",
         "city": "San Francisco",
         "state": "California"
         }
         */
        Itinerary newItinerary = new Itinerary();
        Location newLocation = new Location();

        // Set location properties
        newLocation.setCity(json.get("city"));
        newLocation.setState(json.get("state"));
        locationRepository.saveAndFlush(newLocation);

        // Set itinerary properties
        Optional<User> existingUser = userRepository.findById(Integer.valueOf(json.get("userId")));
        newItinerary.setUser(existingUser.orElseThrow(() -> new IllegalArgumentException("User with id: " + json.get("userId") + " ,could not be found.") ));
        newItinerary.setLocation(newLocation);
        newItinerary.setTitle(json.get("title"));
        newItinerary.setStart(Date.valueOf(json.get("start")));
        newItinerary.setEnd(Date.valueOf(json.get("end")));
        itineraryRepository.saveAndFlush(newItinerary);

    }

    @Override
    public List<Map<String, Object>> findItineraryByUser(Integer userID) {
        return itineraryRepository.findAllByUser(userID);
    }

    @Override
    public void deleteItineraryById(Integer id){
        itineraryRepository.deleteById(id);
    }
}
