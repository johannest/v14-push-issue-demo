package org.vaadin.johannes;

import java.time.LocalDate;

public class Person {

    private Integer id = 1;

    private String name = "Default asdasd asd asd asdas das das das dasd asd";

    private LocalDate birthday;

    private boolean muggle = true;

    private double bms;

    private Integer age;

    public Person() {
        birthday = LocalDate.now();
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(Integer id, String name, LocalDate birthday) {
        super();
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    public boolean isMuggle() {
        return muggle;
    }

    public void setMuggle(boolean muggle) {
        this.muggle = muggle;
    }

    public double getBms() {
        return bms;
    }

    public void setBms(double bms) {
        this.bms = bms;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}