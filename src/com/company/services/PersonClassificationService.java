package com.company.services;

import com.company.dto.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class PersonClassificationService {

    private final List<Person> persons;

    private final List<Person> masters;

    public PersonClassificationService() {
        this.persons = new ArrayList<>();
        this.masters = new ArrayList<>();
    }

    public List<Person> getAllPersons() {

        return this.persons;
    }

    public List<Person> getAllMasters() {

        return this.masters;
    }

    public List<Person> findPersonsByIerarhieLevel(int level) {

        return getAllPersons().stream().filter(person -> Objects.equals(0, person.getIerarhieLevel())).toList();
    }

    public void classifyPersons(List<String> stringWithPersons) {

        var kingPerson = addKing();

        addOtherMastersAndServants(stringWithPersons);

        setKingsServants(kingPerson);
    }

    private Person addKing() {
        var kingPerson = new Person("Король");
        kingPerson.setIsKing(true);
        addPerson(kingPerson);
        addMaster(kingPerson);

        return kingPerson;
    }

    private void addOtherMastersAndServants(List<String> stringWithPersons) {

        stringWithPersons.forEach(line -> {

            var master = extractMasterWithServantsFromLine(line);
            addPerson(master);
            addMaster(master);
        });
    }

    private void setKingsServants(Person king) {
        getAllPersons().forEach(person -> {
            if (person.isKingsServant()) {
                king.getServants().add(person);
                person.setMaster(king);
            }
        });
    }


    private Person extractMasterWithServantsFromLine(String line) {

        var master = extractMasterFromLine(line);
        var servants = extractServantsFromLine(line);

        master.setServants(servants);

        servants.forEach(servant -> {
            servant.setMaster(master);
        });

        return master;
    }

    private Person extractMasterFromLine(String line) {

        var split = line.split(":");

        var name = split[0];

        return getExistedPersonByNameOrCreateNew(name);
    }

    private List<Person> extractServantsFromLine(String line) {

        var servants = new ArrayList<Person>();

        var split = line.split(":");

        if (split.length <= 1) {
            return servants;
        }

        var servantsString = split[1];
        var servantsSplit = servantsString.split(",");


        Arrays.stream(servantsSplit).forEach(servantName -> {
            var servant = getExistedPersonByNameOrCreateNew(servantName);
            addPerson(servant);

            servants.add(servant);
        });

        return servants;
    }

    private Person getExistedPersonByNameOrCreateNew(String name) {

        name = name.trim();

        var person = findPersonByName(name);
        if (isNull(person)) {
            person = new Person(name);
        }

        return person;
    }


    private Person findPersonByName(String name) {

        return getAllPersons().stream().filter(person -> name.equals(person.getName())).findAny().orElse(null);
    }

    private void addPerson(Person person) {
        getAllPersons().add(person);

    }

    private void addMaster(Person person) {
        getAllMasters().add(person);
    }
}
