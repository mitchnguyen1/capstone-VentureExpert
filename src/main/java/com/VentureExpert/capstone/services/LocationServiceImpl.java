package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;
import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.repositories.ItineraryRepository;
import com.VentureExpert.capstone.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public Integer addLocation(LocationDTO location) {
        Location newLocation = new Location(location);
        Location savedLocation = locationRepository.saveAndFlush(newLocation);
        return savedLocation.getLocationId();
    }


    @Override
    public List<LocationDTO> findLocationForItinerary(Integer id) {
        return null;
    }

    @Override
    public List<LocationDTO> findLocationForTodo(Integer id) {
        return null;
    }

    @Override
    public Optional<LocationDTO> getLocationByID(Long id) {
        return Optional.empty();
    }
}
