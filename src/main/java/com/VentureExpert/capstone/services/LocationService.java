package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;

import java.util.List;

public interface LocationService {

    void addLocation(LocationDTO location);

    List<LocationDTO> getLocationByID(Integer id);

}
