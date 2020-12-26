package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    private PetRepository  petRepository;

    public UserService(EmployeeRepository employeeRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }



    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        customers.stream().forEach(c -> {
            List<Pet> pets = petRepository.findAllByCustomer_Id(c.getId());
            c.setPets(pets);
        });

        return customers;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public Customer getOwnerByPetId(long petId) {
        Customer owner = petRepository.getOne(petId).getCustomer();
        Customer c = customerRepository.findById(owner.getId()).get();
        List<Pet> pets = petRepository.findAllByCustomer_Id(c.getId());
        c.setPets(pets);
        return c;
    }

    public List<Employee> findEmployeesWithSkillAndAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> es = employeeRepository.findAllByDaysAvailableContaining(date.getDayOfWeek());
        List<Employee> es1 = es.stream().filter(e->e.getSkills().containsAll(skills)).collect(Collectors.toList());
        return es1;
    }

    public void updateEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        employeeRepository.getOne(employeeId).setDaysAvailable(daysAvailable);
    }
}
