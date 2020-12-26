package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;
import org.hibernate.mapping.Join;

import javax.persistence.*;

@Data
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private PetType type;
    @Nationalized
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
    private LocalDate birthDate;
    private String notes;
}


