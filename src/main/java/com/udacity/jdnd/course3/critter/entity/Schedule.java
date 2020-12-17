package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class Schedule {

    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

}
