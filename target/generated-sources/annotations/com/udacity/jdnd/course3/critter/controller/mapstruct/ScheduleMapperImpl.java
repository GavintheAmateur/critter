package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.ScheduleDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-26T11:47:44+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class ScheduleMapperImpl extends ScheduleMapper {

    @Override
    public ScheduleDto toScheduleDto(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setEmployeeIds( mapEmployeeListToIDList( schedule.getEmployees() ) );
        scheduleDto.setId( schedule.getId() );
        scheduleDto.setPetIds( mapPetListToIDList( schedule.getPets() ) );
        scheduleDto.setDate( schedule.getDate() );
        Set<EmployeeSkill> set = schedule.getActivities();
        if ( set != null ) {
            scheduleDto.setActivities( new HashSet<EmployeeSkill>( set ) );
        }

        return scheduleDto;
    }

    @Override
    public Schedule toSchedule(ScheduleDto scheduleDto) {
        if ( scheduleDto == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId( scheduleDto.getId() );
        schedule.setDate( scheduleDto.getDate() );
        Set<EmployeeSkill> set = scheduleDto.getActivities();
        if ( set != null ) {
            schedule.setActivities( new HashSet<EmployeeSkill>( set ) );
        }

        return schedule;
    }

    @Override
    public List<Long> mapEmployeeListToIDList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<Long> list = new ArrayList<Long>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( mapEmployeeToEmployeeId( employee ) );
        }

        return list;
    }

    @Override
    public List<Long> mapPetListToIDList(List<Pet> pets) {
        if ( pets == null ) {
            return null;
        }

        List<Long> list = new ArrayList<Long>( pets.size() );
        for ( Pet pet : pets ) {
            list.add( mapPetToPetId( pet ) );
        }

        return list;
    }
}
