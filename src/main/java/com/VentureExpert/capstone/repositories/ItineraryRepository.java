package com.VentureExpert.capstone.repositories;

import com.VentureExpert.capstone.entities.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

}
