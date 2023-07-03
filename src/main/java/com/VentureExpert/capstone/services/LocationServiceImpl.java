package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     *     private Integer id;
     *     private Double lat;
     *     private Double lng;
     *     private String city;
     *     private String state;
     *     private String address;
     *     private Integer zipcode;
     *     private boolean forTodo;
     */
    @Override
    public void addLocation(LocationDTO location) {
        Location newLocation = new Location(location);
        locationRepository.saveAndFlush(newLocation);
        System.out.println(newLocation.getId());
    }


    @Override
    public List<LocationDTO> getLocationByID(Integer id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            LocationDTO locationDTO = new LocationDTO(optionalLocation.get());
            return Collections.singletonList(locationDTO);
        }
        return Collections.emptyList();
    }

}
