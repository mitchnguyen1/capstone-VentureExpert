package com.VentureExpert.capstone.repositories;

import com.VentureExpert.capstone.entities.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

    @Query(value = "SELECT * FROM public.itinerary AS i JOIN public.location AS l ON i.location_id = l.location_id WHERE i.user_id = :userId ORDER BY i.start ASC", nativeQuery = true)
    List<Map<String, Object>> findAllByUser(@Param("userId") Integer userId);


}
