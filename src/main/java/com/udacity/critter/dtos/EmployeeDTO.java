package com.udacity.critter.dtos;

import com.udacity.critter.enums.EmployeeSkill;
import com.udacity.critter.models.Employee;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private Long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public static EmployeeDTO fromEmployee(Employee employee) {
        var employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());

        return employeeDTO;
    }

    public static Employee toEmployee(EmployeeDTO employeeDTO){
        var employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        return employee;
    }
}
