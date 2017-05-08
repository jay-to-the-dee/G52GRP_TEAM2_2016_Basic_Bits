package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.ModuleSemester.*;

enum ModuleSemester {
    WHOLE_YEAR(1), AUTUMN(2), SPRING(3);
    private int order;

    ModuleSemester(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public String toString()
    {
        switch (this.order)
        {
            case 1:
                return "Whole Year";
            case 2:
                return "Autumn";
            case 3:
                return "Spring";
            default:
                return "Unknown Semester";
        }
    }

    public static ModuleSemester toEnum(String string)
    {
        switch (string)
        {
            case "Whole Year":
                return WHOLE_YEAR;
            case "Autumn":
                return AUTUMN;
            case "Spring":
                return SPRING;
            default:
                return null;
        }
    }
};

public class Module implements Comparable<Module>, Serializable {
    private String moduleCode;
    private String moduleName;
    private ModuleSemester moduleSemester;

    private SortedSet<Student> enrolled;
    private SortedSet<Staff> lecturers;

    public Module(String moduleCode, String moduleName, String moduleSemester) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;

        this.moduleSemester = ModuleSemester.toEnum(moduleSemester);

        enrolled = new ConcurrentSkipListSet<>();
        lecturers = new ConcurrentSkipListSet<>();
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleSemester='" + this.moduleSemester.toString() + '\'' +
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
