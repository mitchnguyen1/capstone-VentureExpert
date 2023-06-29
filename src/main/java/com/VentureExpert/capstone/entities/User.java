package com.VentureExpert.capstone.entities;

import com.VentureExpert.capstone.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Itinerary> itineraries = new ArrayList<>();
    public User(UserDTO user){
        if(user.getUsername() != null ){
            this.username = user.getUsername();
        }
        if(user.getPassword() != null){
            this.password = user.getPassword();
        }
    }

}
