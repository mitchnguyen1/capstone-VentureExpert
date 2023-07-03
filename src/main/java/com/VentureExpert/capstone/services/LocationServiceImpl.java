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


    @Override
    public void addLocation(LocationDTO locationDTO) {
        Location newLocation = new Location(locationDTO);
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

    @Override
    public void updateLocation(LocationDTO locationDTO) {
        Optional<Location> locationOptional = locationRepository.findById(locationDTO.getId());
        locationOptional.ifPresent(location -> {
            if (locationDTO.getLat() != null) {
                location.setLat(locationDTO.getLat());
            }
            if (locationDTO.getLng() != null) {
                location.setLng(locationDTO.getLng());
            }
            if (locationDTO.getCity() != null) {
                location.setCity(locationDTO.getCity());
            }
            if (locationDTO.getState() != null) {
                location.setState(locationDTO.getState());
            }
            if (locationDTO.getAddress() != null) {
                location.setAddress(locationDTO.getAddress());
            }
            if (locationDTO.getZipcode() != null) {
                location.setZipcode(locationDTO.getZipcode());
            }
            locationRepository.saveAndFlush(location);
        });
    }


    @Override
    public void deleteLocation(Integer id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if(optionalLocation.isPresent()){
            locationRepository.deleteById(id);
        }
    }

}
