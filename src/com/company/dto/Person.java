package com.company.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class Person {

    private String name;

    private List<Person> servants;

    private Person master;

    private Boolean isKing = false;

    public Person(String name) {
        this.name = name;

        servants = new ArrayList<>();
    }

    public boolean isKingsServant() {

        return !isKing && isNull(getMaster());
    }

    public int getIerarhieLevel() {

        var currentLevel = 0;
        if (!isNull(master)) {
            currentLevel++;
            currentLevel = currentLevel + master.getIerarhieLevel();
        }

        return currentLevel;
    }

    public void Print() {
        var level = getIerarhieLevel()*4;
        var spaces = "";
        for(int i = 0 ; i< level; i++) {
            spaces = spaces + " ";
        }
        System.out.print(spaces);

        System.out.println(name + String.format("(servants count: %s, level: %s)", servants.size(), getIerarhieLevel()));

        if (servants.size() > 0){
            servants.sort(Comparator.comparing(Person::getName));
            servants.forEach(Person::Print);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", servants=" + servants +
                ", isKing=" + isKing +
                '}';
    }


}
