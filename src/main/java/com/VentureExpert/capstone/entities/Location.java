package com.VentureExpert.capstone.entities;

import com.VentureExpert.capstone.dtos.LocationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer locationId;

    @Column(nullable = true)
    private Double lat;

    @Column(nullable = true)
    private Double lng;

    @Column
    private String city;

    @Column
    private String state;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private Integer zipcode;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todos;

    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY)
    private Itinerary itinerary;

    public Location(LocationDTO location) {
        this.locationId = location.getLocationId();
        this.lat = location.getLat();
        this.lng = location.getLng();
        this.city = location.getCity();
        this.state = location.getState();
        this.address = location.getAddress();
        this.zipcode = location.getZipcode();
    }
}
