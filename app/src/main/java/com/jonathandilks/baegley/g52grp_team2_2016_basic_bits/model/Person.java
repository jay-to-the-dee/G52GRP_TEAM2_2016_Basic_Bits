package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model;

public abstract class Person {
    private String name;
    private String email;
    private String userName;

    public Person(String name, String email, String userName) {
        this.name = name;
        this.email = email;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
