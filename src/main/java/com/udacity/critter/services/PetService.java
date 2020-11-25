package com.udacity.critter.services;

import com.udacity.critter.exceptions.PetNotFoundException;
import com.udacity.critter.models.Customer;
import com.udacity.critter.models.Pet;
import com.udacity.critter.repositories.CustomerRepository;
import com.udacity.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> findByCustomerId(Long customerId) {
        return petRepository.findByCustomerId(customerId);
    }

    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);

        if (pet.getId() != null) {
            Pet finalPet = pet;
            return petRepository.findById(pet.getId())
                    .map(petToBeUpdated -> {
                        petToBeUpdated.setName(finalPet.getName());
                        petToBeUpdated.setNotes(finalPet.getNotes());

                        return petRepository.save(petToBeUpdated);
                    }).orElseThrow(PetNotFoundException::new);
        }

        pet.setCustomer(customer);
        pet = petRepository.save(pet);

        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);

        return pet;
    }
}
