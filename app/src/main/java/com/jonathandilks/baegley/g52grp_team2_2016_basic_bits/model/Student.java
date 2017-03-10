package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Student extends Person {
    private Staff tutor;
    private SortedSet<Module> modulesEnrolled;

    public Student(String name, String email, String userName, Staff tutor) {
        super(name, email, userName);
        this.tutor = tutor;

        tutor.addStudent(this);
        modulesEnrolled = new ConcurrentSkipListSet<>();
    }

    public Staff getTutor() {
        return tutor;
    }

    public SortedSet<Module> getModulesEnrolled() {
        return modulesEnrolled;
    }

    protected void addModuleEnrolment(Module module) {
        modulesEnrolled.add(module);
    }

}
