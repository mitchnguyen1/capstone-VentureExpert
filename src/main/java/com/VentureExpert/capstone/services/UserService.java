package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.UserDTO;
import com.VentureExpert.capstone.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    @Transactional
    List<String> addUser(UserDTO userDto);

    List<String> userLogin(UserDTO userDto);

    String getUserFullNameById(Integer id);
}
