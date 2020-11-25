package com.udacity.critter.services;

import com.udacity.critter.models.Schedule;
import com.udacity.critter.repositories.CustomerRepository;
import com.udacity.critter.repositories.EmployeeRepository;
import com.udacity.critter.repositories.PetRepository;
import com.udacity.critter.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository, ScheduleRepository scheduleRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetId(long petId) {
        var pet = petRepository.getOne(petId);

        return scheduleRepository.findByPets(pet);
    }

    public List<Schedule> findByEmployeeId(long employeeId) {
        var employee = employeeRepository.getOne(employeeId);

        return scheduleRepository.findByEmployees(employee);
    }

    public List<Schedule> findByCustomerId(long customerId) {
        var customer = customerRepository.getOne(customerId);

        return scheduleRepository.findByPetsIn(customer.getPets());
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        var employees = employeeRepository.findAllById(employeeIds);
        var pets = petRepository.findAllById(petIds);

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }
}
