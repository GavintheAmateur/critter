package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Employee {
    @Id
    private long id;
    private String name;
    @ElementCollection
    private Set<EmployeeSkill> skills;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

}