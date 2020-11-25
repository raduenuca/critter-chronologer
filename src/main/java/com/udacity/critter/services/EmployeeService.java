package com.udacity.critter.services;

import com.udacity.critter.enums.EmployeeSkill;
import com.udacity.critter.exceptions.EmployeeNotFoundException;
import com.udacity.critter.models.Employee;
import com.udacity.critter.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee){
        if (employee.getId() != null) {
            return employeeRepository.findById(employee.getId())
                    .map(employeeToBeUpdated -> {
                        employeeToBeUpdated.setName(employee.getName());
                        employeeToBeUpdated.setSkills(employee.getSkills());
                        employeeToBeUpdated.setDaysAvailable(employee.getDaysAvailable());

                        return employeeRepository.save(employeeToBeUpdated);
                    }).orElseThrow(EmployeeNotFoundException::new);
        }

        return employeeRepository.save(employee);
    }

    public Employee findById(Long id){
        return employeeRepository.getOne(id);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        var employee = this.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findByService(LocalDate date, Set<EmployeeSkill> skills) {
        return employeeRepository
                .findByDaysAvailable(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
