package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Useful Dummy data for testing
 */
public class DummyData {
    private SortedSet<Staff> staff;
    private SortedSet<Student> students;
    private SortedSet<Module> modules;

    public DummyData() {
        /* Create Staff */
        staff = new ConcurrentSkipListSet<>();
        Staff hagrid = new Staff("Hagrid", "hagrid@hogwarts.com", "psyhag", "0800001066", "Hut Thing", "http://ilikebeardsandowls.com");
        Staff umbridge = new Staff("Umbridge", "umbridge@hogwarts.com", "psyumb", "0800001067", "Evil Lair", "http://iamveryevilandmean.com");
        Staff dumbledore = new Staff("Dumbledore", "dumbledore@hogwarts.com", "psydum", "0800001068", "The Best Office in the School", "http://wizardprof.com");

        staff.add(hagrid);
        staff.add(umbridge);
        staff.add(dumbledore);

        /* Create Students */
        students = new ConcurrentSkipListSet<>();
        /* Dumbledore's enrolled*/
        Student harry = new Student("Harry Potter", "harry@aol.com", "psypotter", dumbledore);
        Student ron = new Student("Ron Weasley", "ron@yahoo.com", "psyweasley", dumbledore);

        /* Hagrid's enrolled*/
        Student hermione = new Student("Hermione Granger", "hermione@hotmail.com", "psygranger", hagrid);

        /* Umbridge's enrolled*/
        Student malfoy = new Student("Draco Malfoy", "draco@malfoyresidence.com", "psymalfoyd", umbridge);
        Student volderz = new Student("Tom Riddle", "tom@HeWhoMustNotBeNamed.com", "", umbridge);

        students.add(harry);
        students.add(ron);
        students.add(hermione);
        students.add(malfoy);
        students.add(volderz);

        /* Make modules*/
        modules = new ConcurrentSkipListSet<>();
        Module g51fun = new Module("G51FUN", "Functional Magical Paradigms", ModuleSemester.SPRING, hagrid);
        Module g51wiz = new Module("G51WIZ", "Intoduction to Wizardry", ModuleSemester.AUTUMN, dumbledore);
        Module g52grp = new Module("G52GRP", "Ghastly Ridiculous Project", ModuleSemester.WHOLE_YEAR, umbridge);
        Module g51bad = new Module("G51BAD", "Paradigms of Evil", ModuleSemester.SPRING, umbridge);


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

        modules.add(g51wiz);
        modules.add(g52grp);
        modules.add(g51fun);
        modules.add(g51bad);
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
}
