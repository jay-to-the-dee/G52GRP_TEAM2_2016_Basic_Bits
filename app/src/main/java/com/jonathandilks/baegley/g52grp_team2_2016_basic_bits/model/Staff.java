package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Staff extends Person {
    private String phoneNo;
    private String office;
    private String webPageURL;
    private SortedSet<Student> tutees;
    private SortedSet<Module> modulesTaught;

    public Staff(String name, String email, String userName, String phoneNo, String office, String webPageURL) {
        super(name, email, userName);
        this.phoneNo = phoneNo;
        this.office = office;
        this.webPageURL = webPageURL;
        tutees = new ConcurrentSkipListSet<>();
        modulesTaught = new ConcurrentSkipListSet<>();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getOffice() {
        return office;
    }

    public String getWebPageURL() {
        return webPageURL;
    }

    public SortedSet<Student> getTutees() {
        return tutees;
    }

    public SortedSet<Module> getModulesTaught() {
        return modulesTaught;
    }

    protected void addStudent(Student student) {
        tutees.add(student);
    }

    protected void addModuleTeaching(Module module) {
        modulesTaught.add(module);
    }
}
