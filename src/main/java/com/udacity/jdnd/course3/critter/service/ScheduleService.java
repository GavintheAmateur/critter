package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {


    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;
    private CustomerRepository customerRepository;



    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule createSchedule(LocalDate date, List<Long> employeeIds, List<Long> petIds, Set<EmployeeSkill> activities) {
        Schedule s = new Schedule();
        s.setDate(date);
        s.setEmployees(
                employeeIds
                .stream()
                .map(id->employeeRepository.getOne(id))
                .collect(Collectors.toList())
        );
        s.setPets(petIds.stream().map(id->petRepository.getOne(id)).collect(Collectors.toList()));
        s.setActivities(activities);
        Schedule schedule = scheduleRepository.save(s);
        return schedule;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findAllByEmployeesContaining(employee);

    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Pet> pets = customer.getPets();
        return scheduleRepository.findAllByPetsIn(pets);

    }
}
