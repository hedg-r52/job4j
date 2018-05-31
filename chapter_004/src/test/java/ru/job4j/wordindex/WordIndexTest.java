package ru.job4j.wordindex;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class WordIndexTest {

    WordIndex indexTrie;

    @Before
    public void setUp() {
        indexTrie = new WordIndex();
        indexTrie.put("test", 1);
        indexTrie.put("text", 5);
        indexTrie.put("test", 10);
        indexTrie.put("test", 15);
        indexTrie.put("text", 20);
    }

    @Test
    public void whenAddTwoWordsWithDifferentPosition() {
        StringBuilder expected = new StringBuilder();
        expected.append("test { 1 10 15 }");
        expected.append(System.lineSeparator());
        expected.append("text { 5 20 }");
        expected.append(System.lineSeparator());
        assertThat(indexTrie.toString(), is(expected.toString()));
    }

    @Test
    public void whenGetIndexesForTestThenGetIndexes1And10And15() {
        Set<Integer> expected = new TreeSet<>();
        expected.add(1);
        expected.add(10);
        expected.add(15);
        Set<Integer> result = indexTrie.getIndexes4Word("test");
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetIndexesForWordWhichNotExistAtTrieThenGetNull() {
        Set<Integer> result = indexTrie.getIndexes4Word("hello");
        assertNull(result);
    }

    @Test
    public void whenLoadWordsFromFile() {
        final String str = "test  text text test test ";
        WordIndex indexTrie = getWordIndexFromFile(str);
        StringBuilder expected = new StringBuilder();
        expected.append("test { 0 16 21 }");
        expected.append(System.lineSeparator());
        expected.append("text { 6 11 }");
        expected.append(System.lineSeparator());
        assertThat(indexTrie.toString(), is(expected.toString()));
    }

    @Test
    public void whenCompareWordsWithStringIndexOf() {
        final String str = "test  text text test test ";
        WordIndex indexTrie = getWordIndexFromFile(str);
        StringBuilder expected = new StringBuilder();
        expected.append(stringIndexesForWord(str, "test"));
        expected.append(System.lineSeparator());
        expected.append(stringIndexesForWord(str, "text"));
        expected.append(System.lineSeparator());
        assertThat(indexTrie.toString(), is(expected.toString()));
    }

    private String stringIndexesForWord(String str, String word) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        result.append(String.format("%s {", word));
        while (count < str.length()) {
            int searchIndex = str.indexOf(word, count);
            if (searchIndex != -1) {
                result.append(String.format(" %s", searchIndex));
                count = searchIndex + 1;
            } else {
                break;
            }
        }
        result.append(" }");
        return result.toString();
    }

    private WordIndex getWordIndexFromFile(String str) {
        final String fileName = "test";
        final String fileExtention = ".tmp";
        WordIndex indexTrie = new WordIndex();
        try {
            File temp = File.createTempFile(fileName, fileExtention);
            FileWriter fw = new FileWriter(temp);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(str);
            }
            indexTrie.loadFile(temp.toString());
            temp.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexTrie;
    }

}