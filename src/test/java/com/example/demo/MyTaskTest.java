package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyTaskTest {
    MyTask myTask = new MyTask();

    public static List<Person> peopleListToTest(){
        List<Person> personList = new ArrayList<>();

        Person person1 = new Person();
        person1.setName("Adam");
        person1.setActive(true);
        person1.setScore(5);
        person1.setTeam(Group.G1);

        Person person2 = new Person();
        person2.setName("Adam4");
        person2.setActive(true);
        person2.setScore(1);
        person2.setTeam(Group.G1);

        Person person3 = new Person();
        person3.setName("Adam2");
        person3.setActive(true);
        person3.setScore(9);
        person3.setTeam(Group.G1);

        Person person4 = new Person();
        person4.setName("Ada5");
        person4.setActive(false);
        person4.setScore(7);
        person4.setTeam(Group.G2);

        Person person5 = new Person();
        person5.setName("Adam");
        person5.setActive(true);
        person5.setScore(3);
        person5.setTeam(Group.G3);

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        personList.add(person5);

        return personList;
    }

    public static List<Person> peopleListToTest2(){
        List<Person> personList = new ArrayList<>();

        Person person1 = new Person();
        person1.setName("Adam");
        person1.setActive(true);
        person1.setScore(5);
        person1.setTeam(Group.G1);

        Person person2 = new Person();
        person2.setName("Adam4");
        person2.setActive(true);
        person2.setScore(1);
        person2.setTeam(Group.G1);

        Person person3 = new Person();
        person3.setName("Adam2");
        person3.setActive(true);
        person3.setScore(1);
        person3.setTeam(Group.G1);

        Person person4 = new Person();
        person4.setName("Ada5");
        person4.setActive(false);
        person4.setScore(7);
        person4.setTeam(Group.G2);

        Person person5 = new Person();
        person5.setName("Adam");
        person5.setActive(true);
        person5.setScore(3);
        person5.setTeam(Group.G3);

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        personList.add(person5);

        return personList;
    }

    @Test
    void check_if_filter_all_records() {
        List<Person> personList = myTask.getActivePlayersByScoreDesc(peopleListToTest());
        int numberOfPeople = personList.size();
        assertEquals(4, numberOfPeople);
    }

    @Test
    void check_if_is_sorted() {
        List<Person> personList = myTask.getActivePlayersByScoreDesc(peopleListToTest());
        System.out.println(personList.toString());
        assertEquals(9, personList.get(0).getScore());
    }

    @Test
    void check_if_getActivePlayersByScoreDesc_returns_only_group_members() {
        List<Person> personList = myTask.getActivePlayersByScoreDesc(peopleListToTest(), Group.G1);
        assertEquals(Group.G1, personList.get(0).getTeam());
        assertEquals(Group.G1, personList.get(1).getTeam());
        assertEquals(Group.G1, personList.get(2).getTeam());
        assertEquals(3,personList.size());
    }

    @Test
    void getGroupWithHighestScore() {
        Group group = myTask.getGroupWithHighestScore(peopleListToTest());
        assertEquals(Group.G1, group);
    }

    @Test
    void getGroupWithHighestScore_test_when_scores_are_equal() {
        Group group = myTask.getGroupWithHighestScore(peopleListToTest2());
        assertEquals(Group.G2, group);
    }

}