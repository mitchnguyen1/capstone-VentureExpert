package com.VentureExpert.capstone.controller;

import com.VentureExpert.capstone.dtos.UserDTO;
import com.VentureExpert.capstone.entities.User;
import com.VentureExpert.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDTO userDto){
        String passHash = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passHash);
        return userService.addUser(userDto);
    }

    @PostMapping("/login")
    public List<String> userLogin(@RequestBody UserDTO userDto){
        return userService.userLogin(userDto);
    }


    @GetMapping("/findById/{id}")
    public String getUserById(@PathVariable Integer id){
        return userService.getUserFullNameById(id);
    }
}
