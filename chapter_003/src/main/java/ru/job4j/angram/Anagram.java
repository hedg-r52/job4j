package ru.job4j.angram;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Анаграмма
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Anagram {
    private String word;

    public Anagram(String word) {
        this.word = word;
    }

    public boolean check(String anagram) {
        boolean result = false;
        if (this.word.length() == anagram.length()) {
            Character[] cWord = ArrayUtils.toObject(this.word.toCharArray());
            ArrayList<Character> listWord = new ArrayList(Arrays.asList(cWord));
            Character[] cAnagram = ArrayUtils.toObject(anagram.toCharArray());
            ArrayList<Character> listAnagram = new ArrayList(Arrays.asList(cAnagram));
            for (Character c : listWord) {
                int index = listAnagram.indexOf(c);
                if (index != -1) {
                    listAnagram.remove(index);
                }
            }
            result = (listAnagram.size() == 0);
        }
        return result;
    }
}
