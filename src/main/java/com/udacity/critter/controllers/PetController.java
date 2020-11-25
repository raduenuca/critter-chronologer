package com.udacity.critter.controllers;

import com.udacity.critter.dtos.PetDTO;
import com.udacity.critter.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetDTO savedPet;
        try {
            savedPet = PetDTO.fromPet(petService.savePet(PetDTO.toPet(petDTO), petDTO.getOwnerId()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save pet", e);
        }

        return savedPet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO existingPet;
        try {
            existingPet = PetDTO.fromPet(petService.findById(petId));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find employee ", e);
        }

        return existingPet;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAll().stream().map(PetDTO::fromPet).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> pets;
        try {
            pets = petService.findByCustomerId(ownerId).stream().map(PetDTO::fromPet).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No pets for given owner id", e);
        }

        return pets;
    }
}
