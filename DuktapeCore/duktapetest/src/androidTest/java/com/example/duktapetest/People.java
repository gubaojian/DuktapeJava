package com.example.duktapetest;

/**
 * Created by furture on 16/8/19.
 * 多态方法
 */
public class People {
    private  Person person;

    private String from;

    public People(Person person) {
        this.person = person;
        this.from = "Person";
    }

    public People(SubPerson person) {
        this.person = person;
        this.from = "SubPerson";
    }

    public String getFrom(){
        return  from;
    }
}
