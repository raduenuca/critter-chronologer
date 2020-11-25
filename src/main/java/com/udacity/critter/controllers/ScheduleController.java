package com.udacity.critter.controllers;

import com.udacity.critter.dtos.ScheduleDTO;
import com.udacity.critter.models.Schedule;
import com.udacity.critter.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO savedSchedule;

        try {
            savedSchedule = ScheduleDTO.fromSchedule(scheduleService.saveSchedule(
                    ScheduleDTO.toSchedule(scheduleDTO),
                    scheduleDTO.getEmployeeIds(),
                    scheduleDTO.getPetIds()
            ));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create schedule", e);
        }

        return savedSchedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream().map(ScheduleDTO::fromSchedule).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> schedules;

        try {
            schedules = scheduleService
                    .findByPetId(petId)
                    .stream()
                    .map(ScheduleDTO::fromSchedule)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet schedule does not exists", e);
        }

        return schedules;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> schedules;

        try {
            schedules = scheduleService
                    .findByEmployeeId(employeeId)
                    .stream()
                    .map(ScheduleDTO::fromSchedule)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee schedule does not exists", e);
        }

        return schedules;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedules;

        try {
            schedules = scheduleService
                    .findByCustomerId(customerId)
                    .stream()
                    .map(ScheduleDTO::fromSchedule)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner schedule does not exists", e);
        }

        return schedules;
    }
}
