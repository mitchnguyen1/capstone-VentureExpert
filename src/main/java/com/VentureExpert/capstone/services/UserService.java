package com.VentureExpert.capstone.services;

import com.VentureExpert.capstone.dtos.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    List<String> addUser(UserDTO userDto);

    List<String> userLogin(UserDTO userDto);
}
