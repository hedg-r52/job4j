package ru.job4j.braces;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Braces
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */


public class Braces {
    final Map<Character, Character> list;

    public Braces() {
        list = new HashMap<>();
    }

    public void add(char ... chars) {
        for (int i = 0; i < chars.length; i += 2) {
            list.put(chars[i], chars[i + 1]);
        }
    }

    public void add(String str) {
        this.add(str.toCharArray());
    }
}
