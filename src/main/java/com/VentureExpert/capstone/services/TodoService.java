package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.TodoDTO;
import com.VentureExpert.capstone.entities.Todo;

import java.util.List;
import java.util.Map;

public interface TodoService {

    void addTodo(Map<String, String> json);

    List<Map<String, Object>> findTodoByItinerary(Integer id);

    void deleteTodo(Integer id);

    void updateTodo(Map<String, String> json);

    List<Map<String, Object>>getTodoById(Integer id);

    void updateComplete(Integer todo_id, boolean update);

}
