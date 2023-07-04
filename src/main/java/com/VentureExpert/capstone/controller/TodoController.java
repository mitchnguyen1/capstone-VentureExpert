package com.VentureExpert.capstone.controller;

import com.VentureExpert.capstone.dtos.TodoDTO;
import com.VentureExpert.capstone.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/add")
    void addTodo(@RequestBody Map<String, String> json) {
        todoService.addTodo(json);
    }

    @GetMapping("/findByItinerary/{itineraryId}")
    List<Map<String, Object>> findTodoByItinerary(@PathVariable Integer itineraryId) {
        return todoService.findTodoByItinerary(itineraryId);
    }

    @DeleteMapping("/delete/{id}")
    void deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("/update/")
    void updateTodo(@RequestBody Map<String, String> json) {
        todoService.updateTodo(json);
    }

    @GetMapping("/getById/{id}")
    List<Map<String, Object>> getTodoByID(@PathVariable Integer id) {
       return todoService.getTodoById(id);
    }

    @PutMapping("/updateComplete/")
    void updateComplete(@RequestParam("todo_id") Integer todoId, @RequestParam("complete") Boolean update) {
        todoService.updateComplete(todoId, update);
    }


}
