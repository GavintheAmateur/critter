package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import java.util.List;

@Data
public class Employee {
    private String name;
    private List<String> skills;

}