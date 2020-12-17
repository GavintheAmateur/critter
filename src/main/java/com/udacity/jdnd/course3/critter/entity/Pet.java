package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;

import PetType;
import lombok.Data;

@Data
public class Pet {

    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;
}
