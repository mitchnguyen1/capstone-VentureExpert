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

        // add location to db
        newLocation.setCity(json.get("city"));
        newLocation.setState(json.get("state"));
        locationRepository.saveAndFlush(newLocation);

        // add itinerary to db
        Optional<User> existingUser = userRepository.findById(Integer.valueOf(json.get("userId")));
        newItinerary.setUser(existingUser.orElseThrow(() -> new IllegalArgumentException("User with id: " + json.get("userId") + " ,could not be found.")));
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
    public void updateItinerary(Map<String, String> json) {
        int itineraryId = Integer.valueOf(json.get("itineraryId"));
        String city = json.get("city");
        String state = json.get("state");
        String title = json.get("title");
        String startStr = json.get("start");
        String endStr = json.get("end");

        Location newLocation = new Location();
        // update location
        if (city != null) {
            newLocation.setCity(json.get("city"));
        }
        if (state != null) {
            newLocation.setState(json.get("state"));
        }
        locationRepository.saveAndFlush(newLocation);


        //update todo
        Optional<Itinerary> newItinerary = itineraryRepository.findById(itineraryId);
        newItinerary.ifPresent(itin -> {
            itin.setLocation(newLocation);
            if (title != null) {
                itin.setTitle(json.get("title"));
            }
            if(startStr != null){
            itin.setStart(Date.valueOf(json.get("start")));
            }
            if(endStr != null){
            itin.setEnd(Date.valueOf(json.get("end")));
            }
            itineraryRepository.saveAndFlush(itin);
        });

    }


    @Override
    public void deleteItineraryById(Integer id) {
        itineraryRepository.deleteById(id);
    }
}
