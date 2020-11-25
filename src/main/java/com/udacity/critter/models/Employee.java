package com.udacity.critter.models;

import com.udacity.critter.enums.EmployeeSkill;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends User {

    @ElementCollection
    @Column(name = "SKILL", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Column(name = "DAY", nullable = false)
    private Set<DayOfWeek> daysAvailable;

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
}
