package com.udacity.jdnd.course3.critter.controller.mapstruct;

import com.udacity.jdnd.course3.critter.controller.dto.EmployeeDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-26T11:47:46+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.8 (AdoptOpenJDK)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEmployee(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( employeeDto.getId() );
        employee.setName( employeeDto.getName() );
        Set<EmployeeSkill> set = employeeDto.getSkills();
        if ( set != null ) {
            employee.setSkills( new HashSet<EmployeeSkill>( set ) );
        }
        Set<DayOfWeek> set1 = employeeDto.getDaysAvailable();
        if ( set1 != null ) {
            employee.setDaysAvailable( new HashSet<DayOfWeek>( set1 ) );
        }

        return employee;
    }

    @Override
    public EmployeeDto toEmployeeDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId( employee.getId() );
        employeeDto.setName( employee.getName() );
        Set<EmployeeSkill> set = employee.getSkills();
        if ( set != null ) {
            employeeDto.setSkills( new HashSet<EmployeeSkill>( set ) );
        }
        Set<DayOfWeek> set1 = employee.getDaysAvailable();
        if ( set1 != null ) {
            employeeDto.setDaysAvailable( new HashSet<DayOfWeek>( set1 ) );
        }

        return employeeDto;
    }
}
