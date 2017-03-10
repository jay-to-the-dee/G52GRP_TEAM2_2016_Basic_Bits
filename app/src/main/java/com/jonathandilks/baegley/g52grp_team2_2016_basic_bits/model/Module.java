package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import android.support.annotation.NonNull;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

enum ModuleSemester {
    WHOLE_YEAR(1), AUTUMN(2), SPRING(3);
    private int order;

    ModuleSemester(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
};

public class Module implements Comparable<Module> {
    private String moduleCode;
    private String moduleName;
    private ModuleSemester moduleSemester;

    private SortedSet<Student> enrolled;
    private SortedSet<Staff> lecturers;

    public Module(String moduleCode, String moduleName, ModuleSemester moduleSemester, Staff lecturer) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleSemester = moduleSemester;

        enrolled = new ConcurrentSkipListSet<>();
        lecturers = new ConcurrentSkipListSet<>();
        lecturers.add(lecturer);
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }

    public SortedSet<Student> getEnrolled() {
        return enrolled;
    }

    public SortedSet<Staff> getLecturers() {
        return lecturers;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public ModuleSemester getModuleSemester() {
        return moduleSemester;
    }

    protected void addStudent(Student student) {
        enrolled.add(student);
        student.addModuleEnrolment(this);
    }

    protected void addLecturers(Staff lecturer) {
        lecturers.add(lecturer);
        lecturer.addModuleTeaching(this);
    }

    @Override
    public int compareTo(@NonNull Module otherModule) {
        final int prefixLength = 3;

        String thisModulePrefix = this.getModuleCode().substring(0, prefixLength);
        String otherModulePrefix = otherModule.getModuleCode().substring(0, prefixLength);
        String thisModuleSuffix = this.getModuleCode().substring(prefixLength);
        String otherModuleSuffix = otherModule.getModuleCode().substring(prefixLength);

        String thisCat = thisModulePrefix + this.getModuleSemester().getOrder() + thisModuleSuffix;
        String otherCat = otherModulePrefix + otherModule.getModuleSemester().getOrder() + otherModuleSuffix;
        return thisCat.compareTo(otherCat);
    }
}
