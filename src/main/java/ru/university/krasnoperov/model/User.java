package ru.university.krasnoperov.model;

import java.util.UUID;

public class User {

    private String name;
    private int age;
    private boolean sex;
    private String zodiacSign;
    private UUID id = UUID.randomUUID();

    public User(String name, boolean sex, int age, String zodiacSign) {

        this.name = name;
        this.sex = sex;
        this.age = age;

        if (zodiacSign.equals("-")){
            this.zodiacSign = null;
        }else{
            this.zodiacSign = zodiacSign;
        }

    }

    public UUID getId(){
        return id;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name=" + name + "; " + "Age=" + age;
    }
}
