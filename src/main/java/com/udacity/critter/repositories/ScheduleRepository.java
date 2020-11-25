package com.udacity.critter.repositories;

import com.udacity.critter.models.Employee;
import com.udacity.critter.models.Pet;
import com.udacity.critter.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPets(Pet pet);
    List<Schedule> findByPetsIn(List<Pet> pets);
    List<Schedule> findByEmployees(Employee employee);
}
