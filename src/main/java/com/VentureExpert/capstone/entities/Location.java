package com.VentureExpert.capstone.entities;

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

    @Column
    private Float lat;

    @Column
    private Float lng;

    @Column
    private String city;

    @Column
    private String state;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todos;

    @ManyToMany(mappedBy = "locations", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Itinerary> itineraries;
}
