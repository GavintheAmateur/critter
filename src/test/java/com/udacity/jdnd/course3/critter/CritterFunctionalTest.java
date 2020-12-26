package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.udacity.jdnd.course3.critter.controller.*;
import com.udacity.jdnd.course3.critter.controller.dto.*;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 */
@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PetController petController;

    @Autowired
    private ScheduleController scheduleController;

    @Test
    public void testCreateCustomer(){
        CustomerDto customerDto = createCustomerDto();
        CustomerDto newCustomer = userController.saveCustomer(customerDto);
        CustomerDto retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertEquals(newCustomer.getName(), customerDto.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void testCreateEmployee(){
        EmployeeDto employeeDto = createEmployeeDto();
        EmployeeDto newEmployee = userController.saveEmployee(employeeDto);
        EmployeeDto retrievedEmployee = userController.getEmployee(newEmployee.getId());
        Assertions.assertEquals(employeeDto.getSkills(), newEmployee.getSkills());
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDto customerDto = createCustomerDto();
        CustomerDto newCustomer = userController.saveCustomer(customerDto);

        PetDto petDto = createPetDto();
        petDto.setOwnerId(newCustomer.getId());
        PetDto newPet = petController.savePet(petDto);

        //make sure pet contains customer id
        PetDto retrievedPet = petController.getPet(newPet.getId());
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getOwnerId(), newCustomer.getId());

        //make sure you can retrieve pets by owner
        List<PetDto> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

        //check to make sure customer now also contains pet
        CustomerDto retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertTrue(retrievedCustomer.getPetIds() != null && retrievedCustomer.getPetIds().size() > 0);
        Assertions.assertEquals(retrievedCustomer.getPetIds().get(0), retrievedPet.getId());
    }

    @Test
    public void testFindPetsByOwner() {
        CustomerDto customerDto = createCustomerDto();
        CustomerDto newCustomer = userController.saveCustomer(customerDto);

        PetDto petDto = createPetDto();
        petDto.setOwnerId(newCustomer.getId());
        PetDto newPet = petController.savePet(petDto);
        petDto.setType(PetType.DOG);
        petDto.setName("DogName");
        PetDto newPet2 = petController.savePet(petDto);

        List<PetDto> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getOwnerId(), newCustomer.getId());
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testFindOwnerByPet() {
        CustomerDto customerDto = createCustomerDto();
        CustomerDto newCustomer = userController.saveCustomer(customerDto);

        PetDto petDto = createPetDto();
        petDto.setOwnerId(newCustomer.getId());
        PetDto newPet = petController.savePet(petDto);

        CustomerDto owner = userController.getOwnerByPet(newPet.getId());
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        Assertions.assertEquals(owner.getPetIds().get(0), newPet.getId());
    }

    @Test
    public void testChangeEmployeeAvailability() {
        EmployeeDto employeeDto = createEmployeeDto();
        EmployeeDto emp1 = userController.saveEmployee(employeeDto);
        Assertions.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        userController.setAvailability(availability, emp1.getId());

        EmployeeDto emp2 = userController.getEmployee(emp1.getId());
        Assertions.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        EmployeeDto emp1 = createEmployeeDto();
        EmployeeDto emp2 = createEmployeeDto();
        EmployeeDto emp3 = createEmployeeDto();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        EmployeeDto emp1n = userController.saveEmployee(emp1);
        EmployeeDto emp2n = userController.saveEmployee(emp2);
        EmployeeDto emp3n = userController.saveEmployee(emp3);

        //make a request that matches employee 1 or 2
        EmployeeRequestDto er1 = new EmployeeRequestDto();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).stream().map(EmployeeDto::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDto er2 = new EmployeeRequestDto();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).stream().map(EmployeeDto::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        EmployeeDto employeeTemp = createEmployeeDto();
        employeeTemp.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        EmployeeDto employeeDto = userController.saveEmployee(employeeTemp);
        CustomerDto customerDto = userController.saveCustomer(createCustomerDto());
        PetDto petTemp = createPetDto();
        petTemp.setOwnerId(customerDto.getId());
        PetDto petDto = petController.savePet(petTemp);

        LocalDate date = LocalDate.of(2019, 12, 25);
        List<Long> petList = Lists.newArrayList(petDto.getId());
        List<Long> employeeList = Lists.newArrayList(employeeDto.getId());
        Set<EmployeeSkill> skillSet =  Sets.newHashSet(EmployeeSkill.PETTING);

        scheduleController.createSchedule(createScheduleDto(petList, employeeList, date, skillSet));
        ScheduleDto scheduleDto = scheduleController.getAllSchedules().get(0);

        Assertions.assertEquals(scheduleDto.getActivities(), skillSet);
        Assertions.assertEquals(scheduleDto.getDate(), date);
        Assertions.assertEquals(scheduleDto.getEmployeeIds(), employeeList);
        Assertions.assertEquals(scheduleDto.getPetIds(), petList);
    }

    @Test
    public void testFindScheduleByEntities() {
        ScheduleDto sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        ScheduleDto sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(EmployeeSkill.PETTING));

        //add a third schedule that shares some employees and pets with the other schedules
        ScheduleDto sched3 = new ScheduleDto();
        sched3.setEmployeeIds(sched1.getEmployeeIds());
        sched3.setPetIds(sched2.getPetIds());
        sched3.setActivities(Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING));
        sched3.setDate(LocalDate.of(2020, 3, 23));
        scheduleController.createSchedule(sched3);

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

        //Employee 1 in is both schedule 1 and 3
        List<ScheduleDto> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployeeIds().get(0));
        compareSchedules(sched1, scheds1e.get(0));
        compareSchedules(sched3, scheds1e.get(1));

        //Employee 2 is only in schedule 2
        List<ScheduleDto> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployeeIds().get(0));
        compareSchedules(sched2, scheds2e.get(0));

        //Pet 1 is only in schedule 1
        List<ScheduleDto> scheds1p = scheduleController.getScheduleForPet(sched1.getPetIds().get(0));
        compareSchedules(sched1, scheds1p.get(0));

        //Pet from schedule 2 is in both schedules 2 and 3
        List<ScheduleDto> scheds2p = scheduleController.getScheduleForPet(sched2.getPetIds().get(0));
        compareSchedules(sched2, scheds2p.get(0));
        compareSchedules(sched3, scheds2p.get(1));

        //Owner of the first pet will only be in schedule 1
        List<ScheduleDto> scheds1c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched1.getPetIds().get(0)).getId());
        compareSchedules(sched1, scheds1c.get(0));

        //Owner of pet from schedule 2 will be in both schedules 2 and 3
        List<ScheduleDto> scheds2c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched2.getPetIds().get(0)).getId());
        compareSchedules(sched2, scheds2c.get(0));
        compareSchedules(sched3, scheds2c.get(1));
    }


    private static EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("TestEmployee");
        employeeDto.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employeeDto;
    }
    private static CustomerDto createCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("TestEmployee");
        customerDto.setPhoneNumber("123-456-789");
        return customerDto;
    }

    private static PetDto createPetDto() {
        PetDto petDto = new PetDto();
        petDto.setName("TestPet");
        petDto.setType(PetType.CAT);
        return petDto;
    }

    private static EmployeeRequestDto createEmployeeRequestDto() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setDate(LocalDate.of(2019, 12, 25));
        employeeRequestDto.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        return employeeRequestDto;
    }

    private static ScheduleDto createScheduleDto(List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<EmployeeSkill> activities) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setPetIds(petIds);
        scheduleDto.setEmployeeIds(employeeIds);
        scheduleDto.setDate(date);
        scheduleDto.setActivities(activities);
        return scheduleDto;
    }

    private ScheduleDto populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        List<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDto())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e).getId();
                }).collect(Collectors.toList());
        CustomerDto cust = userController.saveCustomer(createCustomerDto());
        List<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDto())
                .map(p -> {
                    p.setOwnerId(cust.getId());
                    return petController.savePet(p).getId();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(createScheduleDto(petIds, employeeIds, date, activities));
    }

    private static void compareSchedules(ScheduleDto sched1, ScheduleDto sched2) {
        Assertions.assertEquals(sched1.getPetIds(), sched2.getPetIds());
        Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assertions.assertEquals(sched1.getEmployeeIds(), sched2.getEmployeeIds());
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
