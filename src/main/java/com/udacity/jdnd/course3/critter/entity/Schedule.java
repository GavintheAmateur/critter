package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Schedule {
    @Id
    private long id;
    @OneToMany
    private List<Employee> employees;
    @OneToMany
    private List<Pet> pets;
    private LocalDate date;
    @ElementCollection
    private Set<EmployeeSkill> activities;

}
