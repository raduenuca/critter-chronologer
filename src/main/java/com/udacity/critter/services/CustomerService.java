package com.udacity.critter.services;

import com.udacity.critter.exceptions.CustomerNotFoundException;
import com.udacity.critter.models.Customer;
import com.udacity.critter.models.Pet;
import com.udacity.critter.repositories.CustomerRepository;
import com.udacity.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findByPetId(Long petId){
        return petRepository.getOne(petId).getCustomer();
    }

    public Customer saveCustomer(Customer customer, List<Long> petIds){
        List<Pet> pets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petIds.stream().map(petRepository::getOne).collect(Collectors.toList());
        }

        if (customer.getId() != null) {
            List<Pet> finalPets = pets;
            return customerRepository.findById(customer.getId())
                    .map(customerToBeUpdated -> {
                        customerToBeUpdated.setNotes(customer.getNotes());
                        customerToBeUpdated.setPhoneNumber(customer.getPhoneNumber());
                        customerToBeUpdated.setName(customer.getName());
                        customerToBeUpdated.setPets(finalPets);

                        return customerRepository.save(customerToBeUpdated);
                    }).orElseThrow(CustomerNotFoundException::new);
        }

        customer.setPets(pets);
        return customerRepository.save(customer);
    }


}
