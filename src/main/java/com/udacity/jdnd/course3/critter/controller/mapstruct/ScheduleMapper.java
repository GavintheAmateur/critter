package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.ScheduleDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ScheduleMapper {
    public static ScheduleMapper MAPPER = Mappers.getMapper(ScheduleMapper.class);
    @Mappings({
            @Mapping(source="employees",target="employeeIds"),
            @Mapping(source="id",target="id"),
            @Mapping(source="pets",target="petIds"),
            @Mapping(source="date",target="date"),
            @Mapping(source="activities",target="activities"),
    })
    public abstract ScheduleDto toScheduleDto(Schedule schedule);
    public  abstract Schedule toSchedule(ScheduleDto scheduleDto);
    public abstract List<Long> mapEmployeeListToIDList(List<Employee> employees);
    public abstract List<Long> mapPetListToIDList(List<Pet> pets);
    public Long mapEmployeeToEmployeeId(Employee employee) {
        return employee.getId();
    }

    public Long mapPetToPetId(Pet pet) {
        return pet.getId();
    }
}
