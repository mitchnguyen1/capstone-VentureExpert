package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Integer addLocation(LocationDTO location);

    List<LocationDTO> findLocationForItinerary(Integer id);
    List<LocationDTO> findLocationForTodo(Integer id);
    Optional<LocationDTO> getLocationByID(Long id);

}
