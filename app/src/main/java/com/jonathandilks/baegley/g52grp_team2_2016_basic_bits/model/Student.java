package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person {
    private Staff tutor;
    private Set<Module> modulesEnrolled;

    public Student(String name, String email, String userName, Staff tutor) {
        super(name, email, userName);
        this.tutor = tutor;

        tutor.addStudent(this);
        modulesEnrolled = new HashSet<>();
    }

    public Staff getTutor() {
        return tutor;
    }

    protected void addModuleEnrolment(Module module) {
        modulesEnrolled.add(module);
    }
}
