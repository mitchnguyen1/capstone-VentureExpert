package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.TodoDTO;
import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.Todo;
import com.VentureExpert.capstone.repositories.ItineraryRepository;
import com.VentureExpert.capstone.repositories.LocationRepository;
import com.VentureExpert.capstone.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    /**
     * private Itinerary itinerary;
     * private String city,State,address,zipcode
     * private String title;
     * private Date date;
     * private Time start;
     * private Time end;
     * private Double cost;
     * private boolean complete;
     */

    @Override
    public void addTodo(Map<String, String> json) {
        Todo newTodo = new Todo();
        Location newLocation = new Location();

        // Add location
        newLocation.setState(json.get("state"));
        newLocation.setCity(json.get("city"));
        newLocation.setAddress(json.get("address"));
        newLocation.setZipcode(Integer.valueOf(json.get("zipcode")));
        locationRepository.saveAndFlush(newLocation);

        // Add todo
        Optional<Itinerary> itinerary = itineraryRepository.findById(Integer.valueOf(json.get("itineraryId")));
        newTodo.setLocation(newLocation);
        newTodo.setItinerary(itinerary.orElseThrow(() -> new IllegalArgumentException("Itinerary with id: " + json.get("itineraryId") + " could not be found.")));
        newTodo.setTitle(json.get("title"));
        newTodo.setDate(Date.valueOf(json.get("date")));

        // Parse time if start and end values are provided
        String startTime = json.get("start");
        String endTime = json.get("end");

        if(startTime.equals(":00")){
            newTodo.setStart(null);
        }else{
            newTodo.setStart(Time.valueOf(startTime));
        }
        if(endTime.equals(":00")){
            newTodo.setEnd(null);
        }else{
            newTodo.setEnd(Time.valueOf(endTime));
        }
        // Parse cost if it is not an empty string
        String costValue = json.get("cost");
        double cost = costValue.isEmpty() ? 0.0 : Double.valueOf(costValue);
        newTodo.setCost(cost);

        newTodo.setComplete(Boolean.parseBoolean(json.get("complete")));
        todoRepository.saveAndFlush(newTodo);
    }


    @Override
    public List<Map<String, Object>> findTodoByItinerary(Integer itineraryId) {
        return todoRepository.findAllByItinerary(itineraryId);
    }

    @Override
    public void deleteTodo(Integer id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void updateTodo(Map<String, String> json) {

        Location newLocation = new Location();

        // Add location
        newLocation.setState(json.get("state"));
        newLocation.setCity(json.get("city"));
        newLocation.setAddress(json.get("address"));
        newLocation.setZipcode(Integer.valueOf(json.get("zipcode")));
        locationRepository.saveAndFlush(newLocation);


        //update todo
        Optional<Todo> newTodo = todoRepository.findById(Integer.valueOf(json.get("todo_id")));
        newTodo.ifPresent(todo -> {
            todo.setLocation(newLocation);
            todo.setTitle(json.get("title"));
            todo.setDate(Date.valueOf(json.get("date")));
            //parse time
            todo.setStart(Time.valueOf(json.get("start")));
            todo.setEnd(Time.valueOf(json.get("end")));
            System.out.println("Start+++" + json.get("start"));
            todo.setCost(Double.valueOf(json.get("cost")));
            todo.setComplete(Boolean.parseBoolean(json.get("complete")));
            todoRepository.saveAndFlush(todo);
        });

    }

    @Override
    public List<Map<String, Object>> getTodoById(Integer id) {
        return todoRepository.findByTodoId(id);
    }

    @Override
    public void updateComplete(Integer todo_id, boolean update) {
        Optional<Todo> newTodo = todoRepository.findById(todo_id);
        newTodo.ifPresent(todo -> {
            todo.setComplete(update);
            todoRepository.saveAndFlush(todo);
        });
    }


}
