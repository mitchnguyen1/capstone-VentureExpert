package com.VentureExpert.capstone.repositories;

import com.VentureExpert.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
