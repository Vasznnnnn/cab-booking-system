package com.myapps.cabs.model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

    private String type;

    private String description;

    private String coordinates;

}
