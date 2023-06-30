package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Integer id;
    private Double lat;
    private Double lng;
    private String city;
    private String state;
    private String address;
    private Integer zipcode;
    private boolean forTodo;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.lat = location.getLat();
        this.lng = location.getLng();
        this.city = location.getCity();
        this.state = location.getState();
        this.address = location.getAddress();
        this.zipcode = location.getZipcode();
        this.forTodo = location.isForTodo();
    }
}
