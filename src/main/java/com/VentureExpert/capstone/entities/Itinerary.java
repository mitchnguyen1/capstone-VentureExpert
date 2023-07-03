package com.VentureExpert.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "itinerary")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Integer id;

    @Column
    private String title;

    @Column
    private Date start;

    @Column(name = "\"end\"")
    private Date end;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "itinerary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    public Itinerary(Itinerary itinerary) {
        this.id = itinerary.getId();
        this.title = itinerary.getTitle();
        this.start = itinerary.getStart();
        this.end = itinerary.getEnd();
        this.user = itinerary.getUser();
        this.location = itinerary.getLocation();
    }

}

