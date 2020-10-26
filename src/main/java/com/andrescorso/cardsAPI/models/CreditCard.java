package com.andrescorso.cardsAPI.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity @Getter @Setter
public class CreditCard {
    @Id
    private String numberCC;
    private String cvv;
    private String expiration_date;
    @ManyToOne
    private Customer owner;
}
