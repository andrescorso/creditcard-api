package com.andrescorso.cardsAPI.repositories;

import com.andrescorso.cardsAPI.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);

}
