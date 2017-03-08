package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Useful Dummy data for testing
 */
public class DummyData {
    private Set<Staff> staff;
    private Set<Student> students;

    public DummyData() {
        /* Create Staff */
        staff = new HashSet<>();
        Staff hagrid = new Staff("Hagrid", "hagrid@hogwarts.com", "psyhag", "0800001066", "Hut Thing", "http://ilikebeardsandowls.com");
        Staff umbridge = new Staff("Umbridge", "umbridge@hogwarts.com", "psyumb", "0800001067", "Evil Lair", "http://iamveryevilandmean.com");
        Staff dumbledore = new Staff("Dumbledore", "dumbledore@hogwarts.com", "psydum", "0800001068", "The Best Office in the School", "http://wizardprof.com");

        staff.add(hagrid);
        staff.add(umbridge);
        staff.add(dumbledore);

        /* Create Students */
        students = new HashSet<>();
        /* Dumbledore's enrolled*/
        Student harry = new Student("Harry Potter", "harry@aol.com", "potterrr", dumbledore);
        Student ron = new Student("Ron Weesley", "ron@yahoo.com", "potterrr", dumbledore);

        /* Hermione's enrolled*/
        Student hermione = new Student("Hermione Granger", "hermione@hotmail.com", "potterrr", hagrid);

        /* Umbridge's enrolled*/
        Student malfoy = new Student("Draco Malfoy", "draco@malfoyresidence.com", "draco", umbridge);
        Student volderz = new Student("Tom Riddle", "tom@voldermort.com", "", umbridge);

        students.add(harry);
        students.add(ron);
        students.add(hermione);
        students.add(malfoy);
        students.add(volderz);

        /* Make modules*/
        Module g51wiz = new Module("G51WIZ", "Intoduction to Wizardry", dumbledore);
        Module g51fun = new Module("G51FUN", "Functional Magical Paradigms", hagrid);
        Module g51bad = new Module("G51BAD", "Paradigms of Evil", umbridge);

        /*Now give modules students */
        g51wiz.addStudent(harry);
        g51wiz.addStudent(ron);

        g51fun.addStudent(hermione);
        g51fun.addStudent(harry);
        g51fun.addStudent(malfoy);
        g51fun.addStudent(volderz);

        g51bad.addStudent(malfoy);
        g51bad.addStudent(volderz);
        g51bad.addStudent(harry);
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Person> getEveryone() {
        Set<Person> everyone = new HashSet<>();
        everyone.addAll(staff);
        everyone.addAll(students);
        return everyone;
    }
}
