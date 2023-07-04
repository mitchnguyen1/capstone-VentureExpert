package com.VentureExpert.capstone.repositories;

import com.VentureExpert.capstone.dtos.TodoDTO;
import com.VentureExpert.capstone.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    @Query(value = "SELECT * FROM public.todo AS t JOIN public.location AS l ON t.location_id = l.location_id WHERE t.itinerary_id = :itineraryId", nativeQuery = true)
    List<Map<String, Object>> findAllByItinerary(@Param("itineraryId") Integer itineraryId);

    @Query(value = "SELECT * FROM public.todo AS t JOIN public.location AS l ON t.location_id = l.location_id WHERE t.todo_id = :todoId", nativeQuery = true)
    List<Map<String, Object>> findByTodoId(@Param("todoId") Integer todoId);
}
