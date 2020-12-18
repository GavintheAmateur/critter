package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    @OneToMany
    private List<Pet> pets;
}
