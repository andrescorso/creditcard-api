package com.andrescorso.cardsAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id
    private Integer customer_id;
    private String name;
    private String surname;
}
