package com.andrescorso.cardsAPI.controllers;

import com.andrescorso.cardsAPI.models.CreditCard;
import com.andrescorso.cardsAPI.models.Customer;
import com.andrescorso.cardsAPI.repositories.CreditCardRepository;
import com.andrescorso.cardsAPI.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/creditcard")
public class CreditCardController {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<CreditCard> getAllCreditCards(){
        return  creditCardRepository.findAll();
    }

    @PostMapping("/register/{customer_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard addCreditCard(@PathVariable("customer_id") Integer customer_id, @RequestBody CreditCard creditCard){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer do not exists!");
        }
        if (!creditCardRepository.findById(creditCard.getNumberCC()).isPresent()) {
            creditCard.setOwner(customer.get());
            return creditCardRepository.save(creditCard);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "CreditCard already exists!");
    }
    @GetMapping("/find/{numberCC}")
    public Optional<CreditCard> getCreditCardsByNumber(@PathVariable("numberCC") String numberCC){
        return creditCardRepository.findById(numberCC);
    }
    @GetMapping("/find/customer/{customer_id}")
    public Iterable<CreditCard> getCreditCardsByCustomer(@PathVariable("customer_id") Integer customer_id){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer do not exists!");
        }
        return creditCardRepository.findAllByOwner(customer.get());
    }

    @PutMapping("/update/{customer_id}")
    public CreditCard updateCreditCard(@PathVariable("customer_id") Integer customer_id, @RequestBody CreditCard creditCard){
        Optional<Customer> customer = customerRepository.findById(customer_id);
        if (!customer.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer do not exists!");
        }
        if (!creditCardRepository.findById(creditCard.getNumberCC()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CreditCard do not exists!");
        }
        creditCard.setOwner(customer.get());
        return creditCardRepository.save(creditCard);
    }



}
