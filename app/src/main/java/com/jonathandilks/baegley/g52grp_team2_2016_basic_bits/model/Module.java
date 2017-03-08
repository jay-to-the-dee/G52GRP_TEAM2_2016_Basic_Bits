package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.HashSet;
import java.util.Set;

public class Module {
    private String moduleCode;
    private String moduleName;

    private Set<Student> enrolled;
    private Set<Staff> lecturers;

    public Module(String moduleCode, String moduleName, Staff lecturer) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;

        enrolled = new HashSet<>();
        lecturers = new HashSet<>();
        lecturers.add(lecturer);
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }

    public Set<Student> getEnrolled() {
        return enrolled;
    }

    public Set<Staff> getLecturers() {
        return lecturers;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    protected void addStudent(Student student) {
        enrolled.add(student);
        student.addModuleEnrolment(this);
    }

    protected void addLecturers(Staff lecturer) {
        lecturers.add(lecturer);
        lecturer.addModuleTeaching(this);
    }
}
