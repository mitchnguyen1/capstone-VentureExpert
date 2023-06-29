package com.VentureExpert.capstone.dtos;

import com.VentureExpert.capstone.entities.Itinerary;
import com.VentureExpert.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {


    private Integer userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private List<Itinerary> itineraries;

    public UserDTO(User user) {
        if (user.getUserId() != null) {
            this.userId = user.getUserId();
        }
        if (user.getUsername() != null) {
            this.username = user.getUsername();
        }
        if (user.getPassword() != null) {
            this.password = user.getPassword();
        }
        if (user.getEmail() != null) {
            this.password = user.getEmail();
        }

    }
}
