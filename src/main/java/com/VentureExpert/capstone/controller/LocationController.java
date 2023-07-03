package com.VentureExpert.capstone.controller;

import com.VentureExpert.capstone.dtos.LocationDTO;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.repositories.UserRepository;
import com.VentureExpert.capstone.services.LocationService;
import com.VentureExpert.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;


    @PostMapping("/add")
    void addLocation(@RequestBody LocationDTO locationDTO){
        locationService.addLocation(locationDTO);
    }

    @GetMapping("/findById/{id}")
    public List<LocationDTO> getLocationById(@PathVariable Integer id){
        return locationService.getLocationByID(id);
    }

    @PutMapping("/update")
    void updateLocation(@RequestBody LocationDTO locationDTO){
        locationService.updateLocation(locationDTO);
    }

    @DeleteMapping("/delete/{id}")
    void deleteLocation(@PathVariable Integer id){
        locationService.deleteLocation(id);
    }
}
