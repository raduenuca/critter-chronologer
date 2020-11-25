package com.udacity.critter.controllers;

import com.udacity.critter.dtos.CustomerDTO;
import com.udacity.critter.dtos.EmployeeDTO;
import com.udacity.critter.dtos.EmployeeRequestDTO;
import com.udacity.critter.services.CustomerService;
import com.udacity.critter.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomer;
        try {
            savedCustomer = CustomerDTO.fromCustomer(customerService.saveCustomer(
                    CustomerDTO.toCustomer(customerDTO),
                    customerDTO.getPetIds()
            ));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save customer", e);
        }

        return savedCustomer;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.findAll().stream().map(CustomerDTO::fromCustomer).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO existingCustomer;
        try {
            existingCustomer = CustomerDTO.fromCustomer(customerService.findByPetId(petId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find customer for this pet", e);
        }

        return existingCustomer;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee;
        try {
            savedEmployee = EmployeeDTO.fromEmployee(employeeService.saveEmployee(EmployeeDTO.toEmployee(employeeDTO)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save employee", e);
        }

        return savedEmployee;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO existingEmployee;
        try {
            existingEmployee = EmployeeDTO.fromEmployee(employeeService.findById(employeeId));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find employee ", e);
        }

        return existingEmployee;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setAvailability(daysAvailable, employeeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not set availability for employee with id", e);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService
                .findByService(employeeDTO.getDate(), employeeDTO.getSkills())
                .stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

}
