package com.udacity.critter.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends User {
    //@Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @Column(length = 500)
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
