package com.VentureExpert.capstone.entities;

import com.VentureExpert.capstone.dtos.TodoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Integer todoId;


    @Column
    private String title;

    @Column
    private Date date;

    @Column
    private Time start;

    @Column(name = "\"end\"")
    private Time end;

    @Column
    private Double cost;

    @Column
    private boolean complete;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;


    public Todo(TodoDTO todo) {
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
