package com.andrescorso.cardsAPI.controllers;

import com.andrescorso.cardsAPI.models.Customer;
import com.andrescorso.cardsAPI.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody Customer customer){
        //Add if not exits
        System.out.println(customerRepository.findById(customer.getCustomer_id())) ;
        if (customerRepository.findById(customer.getCustomer_id()).isPresent()) {
            return customerRepository.save(customer);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer already exists!");
    }

    @GetMapping("/find")
    public Iterable<Customer> getCustomerByAttributes(@RequestBody Customer customer) {
        if (Objects.nonNull(customer.getName())) {
            return customerRepository.findAllByName(customer.getName());
        } else if (Objects.nonNull(customer.getSurname())){
            return customerRepository.findAllBySurname(customer.getSurname());
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer do not exists!");
    }
    @GetMapping("/find/{customer_id}")
    public Optional<Customer> getCustomer(@PathVariable("customer_id") String id){
        Optional<Customer> customer = customerRepository.findById(Integer.parseInt(id));
        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer do not exists!");
        }
        return customer;
    }

    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer){
        if (!customerRepository.findById(customer.getCustomer_id()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer does not exists!");
        }
        return customerRepository.save(customer);
    }

}