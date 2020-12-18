package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Pet {
    @Id
    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;
}


