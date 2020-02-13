package org.vaadin.johannes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonService {

    public List<Person> fetch(int offset, int limit) {
        List<Person> list = new ArrayList<>();
        for (int i=0; i<limit; i++) {
            list.add(new Person(UUID.randomUUID().toString()));
        }
        return list;
    }

    public int count() {
        return 500;
    }
}
