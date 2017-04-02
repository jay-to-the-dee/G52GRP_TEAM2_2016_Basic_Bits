package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Data implements Serializable, Parcelable {
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

    public void addModule(Module m) {
        modules.add(m);
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

    protected Data(Parcel in) {
        staff = (SortedSet) in.readValue(SortedSet.class.getClassLoader());
        students = (SortedSet) in.readValue(SortedSet.class.getClassLoader());
        modules = (SortedSet) in.readValue(SortedSet.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(staff);
        dest.writeValue(students);
        dest.writeValue(modules);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}