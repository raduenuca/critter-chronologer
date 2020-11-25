package com.udacity.critter.dtos;

import com.udacity.critter.enums.EmployeeSkill;
import com.udacity.critter.models.Employee;
import com.udacity.critter.models.Pet;
import com.udacity.critter.models.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private Long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ScheduleDTO fromSchedule(Schedule schedule) {
        var scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }

    public static Schedule toSchedule(ScheduleDTO scheduleDTO){
        var schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }
}
