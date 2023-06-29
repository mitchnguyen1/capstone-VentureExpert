package com.VentureExpert.capstone.repositories;

import com.VentureExpert.capstone.entities.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
}
