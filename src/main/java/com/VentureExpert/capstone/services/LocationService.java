package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.LocationDTO;

import java.util.List;

public interface LocationService {

    void addLocation(LocationDTO locationDTO);

    List<LocationDTO> getLocationByID(Integer id);

    void updateLocation(LocationDTO locationDTO);
    void deleteLocation(Integer id);
}
