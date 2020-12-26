package com.udacity.jdnd.course3.critter.controller;


import com.udacity.jdnd.course3.critter.controller.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.controller.dto.EmployeeDto;
import com.udacity.jdnd.course3.critter.controller.dto.EmployeeRequestDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.controller.mapstruct.CustomerMapper;
import com.udacity.jdnd.course3.critter.controller.mapstruct.EmployeeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {



    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        Customer savedCustomer = userService.saveCustomer(customer);
        CustomerDto returnCustomerDto = new CustomerDto();
        BeanUtils.copyProperties(savedCustomer, returnCustomerDto);
        return returnCustomerDto;
    }

    @GetMapping("/customer")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> lc = userService.getAllCustomers();
        return lc.stream().map(c -> getCustomerDto(c)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDto getOwnerByPet(@PathVariable long petId) {
        Customer owner = userService.getOwnerByPetId(petId);
        return CustomerMapper.MAPPER.toCustomerDto(owner);
    }

    @PostMapping("/employee")
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.MAPPER.toEmployee(employeeDto);
        Employee savedEmployee = userService.saveEmployee(employee);
        EmployeeDto dto = EmployeeMapper.MAPPER.toEmployeeDto(savedEmployee);
        return dto;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.getEmployeeById(employeeId);
        EmployeeDto returnEmployeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, returnEmployeeDto);
        return returnEmployeeDto;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.updateEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDto> findEmployeesForService(@RequestBody EmployeeRequestDto employeeRequestDto) {
        List<Employee> l = userService.findEmployeesWithSkillAndAvailability(employeeRequestDto.getSkills(),employeeRequestDto.getDate());
        List<EmployeeDto> lt = l.stream().map(e->EmployeeMapper.MAPPER.toEmployeeDto(e)).collect(Collectors.toList());
        return lt;
    }

    private CustomerDto getCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        List<Pet> pets = Optional.ofNullable(customer.getPets()).orElseGet(Collections::emptyList);
        List<Long> petIds = pets.stream().map(p -> p.getId()).collect(Collectors.toList());
        customerDto.setPetIds(petIds);
        return customerDto;
    }

}
