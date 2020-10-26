package com.andrescorso.cardsAPI.repositories;

import com.andrescorso.cardsAPI.models.CreditCard;
import com.andrescorso.cardsAPI.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCard, String> {

    Iterable<CreditCard> findAllByOwner(Customer owner);

}
