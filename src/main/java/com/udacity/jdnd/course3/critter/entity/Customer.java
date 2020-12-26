package com.udacity.jdnd.course3.critter.entity;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;
}
