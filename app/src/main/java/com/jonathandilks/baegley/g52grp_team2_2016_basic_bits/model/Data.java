package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Data implements Serializable {
    private SortedSet<Staff> staff;
    private SortedSet<Student> students;
    private SortedSet<Module> modules;

    public Data() {
        // create SortedSets
        staff = new ConcurrentSkipListSet<>();
        students = new ConcurrentSkipListSet<>();
        modules = new ConcurrentSkipListSet<>();
    }

    // returns new size of set for debugging
    public int addStudent(Student s) {
        students.add(s);
        return students.size();
    }

    // returns new size of set for debugging
    public int addStaff(Staff s) {
        staff.add(s);
        return staff.size();
    }

    // returns new size of set for debugging
    public int addModule(Module m) {
        modules.add(m);
        return modules.size();
    }

    public SortedSet<Staff> getStaff() {
        return staff;
    }

    public SortedSet<Student> getStudents() {
        return students;
    }

    public SortedSet<Person> getEveryone() {
        SortedSet<Person> everyone = new ConcurrentSkipListSet<>();
        everyone.addAll(staff);
        everyone.addAll(students);
        return everyone;
    }

    public SortedSet<Module> getModules() {
        return modules;
    }

    public SortedSet<Person> getSubset(String searchString)
    {
        SortedSet<Person> everyone = getEveryone();
        SortedSet<Person> results = new ConcurrentSkipListSet<>();

        for (Person e : everyone) {
            if (e.getName().toLowerCase().contains(searchString.toLowerCase())) {
                results.add(e);
            }
        }
        return results;
    }

    public void studentEnrolModule(Module m){
        Iterator<Student> studentIterator = students.iterator();
        while(studentIterator.hasNext())
            studentIterator.next().addModuleEnrolment(m);
    }

    public Student findStudent(String searchString)
    {
        for (Student s : students)
            if(s.getUserName().contains(searchString))
                return s;
        return null;
    }

    public Staff findStaff(String searchString)
    {
        for (Staff s : staff)
            if (s.getUserName().contains(searchString))
                return s;
        return null;
    }
}