package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.controller.dto.ScheduleDto;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.controller.mapstruct.ScheduleMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDto createSchedule(@RequestBody ScheduleDto scheduleDto) {
       Schedule schedule = scheduleService.createSchedule(scheduleDto.getDate(),scheduleDto.getEmployeeIds(),scheduleDto.getPetIds(),scheduleDto.getActivities());
        ScheduleDto st = ScheduleMapper.MAPPER.toScheduleDto(schedule);
        return st;
    }

    @GetMapping
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> ls = scheduleService.getAllSchedules();
        List<ScheduleDto> lst = ls.stream().map(s -> ScheduleMapper.MAPPER.toScheduleDto(s)).collect(Collectors.toList());
        return  lst;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDto> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> ls = scheduleService.getScheduleForPet(petId);
        List<ScheduleDto> lst = ls.stream().map(s -> ScheduleMapper.MAPPER.toScheduleDto(s)).collect(Collectors.toList());
        return lst;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDto> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> ls = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDto> lst = ls.stream().map(s -> ScheduleMapper.MAPPER.toScheduleDto(s)).collect(Collectors.toList());
        return lst;    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDto> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> ls = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDto> lst = ls.stream().map(s -> ScheduleMapper.MAPPER.toScheduleDto(s)).collect(Collectors.toList());
        return lst;     }
}
