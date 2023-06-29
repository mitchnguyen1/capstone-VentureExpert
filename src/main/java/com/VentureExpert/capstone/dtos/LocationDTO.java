package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Integer locationId;
    private Double lat;
    private Double lng;
    private String city;
    private String state;
    private String address;
    private Integer zipcode;

    public LocationDTO(Location location) {
        this.locationId = location.getLocationId();
        this.lat = location.getLat();
        this.lng = location.getLng();
        this.city = location.getCity();
        this.state = location.getState();
        this.address = location.getAddress();
        this.zipcode = location.getZipcode();
    }
}
