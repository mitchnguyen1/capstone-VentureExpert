package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.Location;
import com.VentureExpert.capstone.entities.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Integer todoId;
    private Itinerary itinerary;
    private Location location;
    private String title;
    private Date date;
    private Time start;
    private Time end;
    private Double cost;
    private boolean complete;

    public TodoDTO(Todo todo) {
        this.todoId = todo.getTodoId();
        this.itinerary = todo.getItinerary();
        this.location = todo.getLocation();
        this.title = todo.getTitle();
        this.date = todo.getDate();
        this.start = todo.getStart();
        this.end = todo.getEnd();
        this.cost = todo.getCost();
        this.complete = todo.isComplete();
    }
}
