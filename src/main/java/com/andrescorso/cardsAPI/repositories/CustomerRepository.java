package com.andrescorso.cardsAPI.repositories;

import com.andrescorso.cardsAPI.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    Iterable<Customer> findAllByName(String name);
    Iterable<Customer> findAllBySurname(String surname);
}
