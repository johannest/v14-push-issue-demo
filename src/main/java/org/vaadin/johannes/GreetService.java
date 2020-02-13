package org.vaadin.johannes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    public static final int GREET_COUNT = 1000;

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }

    public List<String> loadGreets(int limit, int offset) {
        List<String> greets = new ArrayList<>();
        for (int i=0; i<GREET_COUNT; i++) {
            greets.add("Hello "+i+" "+ UUID.randomUUID().toString());
        }
        long wait = (long) (Math.random()*1000);
        long st = System.currentTimeMillis();
        while ((System.currentTimeMillis()-st)<wait) {
            Collections.shuffle(greets);
            Collections.sort(greets);
        }
        return greets.subList(offset, offset+limit);
    }
}
