package com.example.volleyball;

public class Person {
    private String name;
    private String age;
    private String gender;
    private String docid;

    public Person() {

    }

    public Person(String name, String age, String gender, String docid) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.docid = docid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
