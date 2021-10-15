package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

public class MyTask {

  /*
  Zwróć listę aktywnych graczy posortowanych po ich wyniku malejąco
   */

    public List<Person> getActivePlayersByScoreDesc(List<Person> people) {
        List<Person> tmpPersonList = people
                .stream()
                .filter(Person::isActive)
                .sorted(Comparator.comparingInt(Person::getScore).reversed())
                .collect(Collectors.toList());
        return tmpPersonList;
    }

  /*
  Zwróć listę aktywnych graczy z danej grupy posortowanych po ich wyniku malejąco
   */

    public List<Person> getActivePlayersByScoreDesc(List<Person> people, Group group) {

        return getActivePlayersByScoreDesc(people)
                .stream()
                .filter(p -> p.getTeam().equals(group))
                .collect(Collectors.toList());
    }

    /*
    Zwróć grupę, która posiada najwyższy wynik. Jeżeli wynik wielu grup jest taki sam, zwróć tę, która ma mniejszą liczbę aktywnych członków.
    Jeżeli ta liczba jest również równa, zwróć którąkolwiek z nich.
     */
    public Group getGroupWithHighestScore(List<Person> people) {
        Map<Group, Integer> groupAndScore = getGroupScoreMap(people);
        Map.Entry<Group, Integer> entry = findGroupWithHighestScore(people, groupAndScore);

        return entry.getKey();
    }



  /*
  Zwróć listę wyników posortowaną malejąco na podstawie ilości punktów per zespół.
  Pojedynczy String powinien mieć format: "NazwaGrupy CałkowityWynik (lista_aktywnych_członków) [ilość nieaktywnych członków]"
   */

    public List<String> printPoints(List<Person> people) {
        List<String> pointsOfAllGroups = new ArrayList<>();

        Map<Group, Integer> groupAndScore = getGroupScoreMap(people);
        Map.Entry<Group, Integer> entry = Collections.max(groupAndScore.entrySet(), Map.Entry.comparingByValue());

        for (int i = 0; i < groupAndScore.size(); i++) {
            String pointsOfOneGroup = prepareString(people, entry);
            pointsOfAllGroups.add(pointsOfOneGroup);
            groupAndScore.remove(entry.getKey());
            if (groupAndScore.size() > 0)
                entry = Collections.max(groupAndScore.entrySet(), Map.Entry.comparingByValue());
        }

        return pointsOfAllGroups;
    }


    private static List<Integer> sumScoresOfGroups(List<Person> people) {
        List<Integer> scores = new ArrayList<>();
        int scoreG1 = 0, scoreG2 = 0, scoreG3 = 0;

        for (Person person : people) {
            Group group = person.getTeam();
            int score = person.getScore();

            if (group.equals(Group.G1)) {
                scoreG1 += score;
            } else if (group.equals(Group.G2)) {
                scoreG2 += score;
            } else scoreG3 += score;
        }

        scores.add(scoreG1);
        scores.add(scoreG2);
        scores.add(scoreG3);

        return scores;
    }

    private static Map<Group, Integer> getGroupScoreMap(List<Person> people) {
        Map<Group, Integer> groupAndScore = new HashMap<>();

        groupAndScore.put(Group.G1, sumScoresOfGroups(people).get(0));
        groupAndScore.put(Group.G2, sumScoresOfGroups(people).get(1));
        groupAndScore.put(Group.G3, sumScoresOfGroups(people).get(2));
        return groupAndScore;
    }

    private Map.Entry<Group, Integer> findGroupWithHighestScore(List<Person> people, Map<Group, Integer> groupAndScore) {
        Map.Entry<Group, Integer> entry = Collections.max(groupAndScore.entrySet(), Map.Entry.comparingByValue());
        for (Map.Entry<Group, Integer> e : groupAndScore.entrySet()) {
            if (!e.getKey().equals(entry.getKey()) && e.getValue().equals(entry.getValue())) {
                int numberOfActivePlayers1 = getActivePlayersByScoreDesc(people, e.getKey()).size();
                int numberOfActivePlayers2 = getActivePlayersByScoreDesc(people, entry.getKey()).size();
                if (numberOfActivePlayers1 < numberOfActivePlayers2) entry = e;
            }
        }
        return entry;
    }

    private static List<Person> inactivePlayersList(List<Person> people, Group group) {

        return people
                .stream()
                .filter(p -> !p.isActive())
                .sorted(Comparator.comparingInt(Person::getScore))
                .filter(p -> p.getTeam().equals(group))
                .collect(Collectors.toList());
    }

    private String prepareString(List<Person> people, Map.Entry<Group, Integer> entry) {
        String groupName = entry.getKey().toString();
        String groupScore = entry.getValue().toString();
        String activeUsers = getActivePlayersByScoreDesc(people, entry.getKey()).toString();
        String otherUsers = inactivePlayersList(people, entry.getKey()).toString();

        return "Nazwa grupy: " + groupName +
                " wynik: " + groupScore +
                " aktywni członkowie: " + activeUsers +
                " nieakywni członkowie: " + otherUsers;
    }


}
