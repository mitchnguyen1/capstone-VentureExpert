package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.UserDTO;
import com.VentureExpert.capstone.entities.User;
import com.VentureExpert.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<String> addUser(UserDTO userDto) {
        /**
         * check if username is avail
         * if is present-return errror
         * else add user
         */
        List<String> response = new ArrayList<>();
        // check if the username is available
        String username = userDto.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            response.add("Username is taken.");
            return response;
        } else {
            User user = new User(userDto);
            userRepository.saveAndFlush(user);
            // adds URL for redirect to the login page after registration
            response.add("http://localhost:8080/login.html");
            return response;
        }
    }


    @Override
    public List<String> userLogin(UserDTO userDto) {
        /**
         * use password encoder to check the db password with the userdto pw
         * if matches-redirect to home page logged in
         * else return error
         */
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //check the passwords with encoder
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                response.add("http://localhost:8080/home.html");
                response.add(String.valueOf(user.getId()));
            } else {
                response.add("Incorrect username or password");
            }
        }
            else{
                response.add("Incorrect email or password");
            }
            return response;
        }
    }

