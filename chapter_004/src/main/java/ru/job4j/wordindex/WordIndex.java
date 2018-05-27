package ru.job4j.wordindex;

import java.io.*;
import java.util.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class WordIndex {
    static class Node {
        Map<Character, Node> children = new TreeMap<>();
        Set<Integer> indexes = new TreeSet<>();
    }

    Node root = new Node();

    public void put(String s, int index) {
        Node node = root;
        for (char ch : s.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new Node());
            }
            node = node.children.get(ch);
        }
        node.indexes.add(index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        toStringRecursion(root, "", result);
        return result.toString();
    }

    private void toStringRecursion(Node node, String substring, StringBuilder result) {
        for (Character ch : node.children.keySet()) {
            Node current = node.children.get(ch);
            substring = String.format("%s%s", substring, ch);
            if (current.indexes.size() > 0) {
                result.append(substring);
                result.append(" {");
                for (Integer i : current.indexes) {
                    result.append(String.format(" %d", i));
                }
                result.append(" }");
                result.append(System.lineSeparator());
            }
            toStringRecursion(current, substring, result);
            substring = substring.substring(0, substring.length() - 1);
        }
    }

    /**
     * Возвращает список позиций слова в файле.
     * Если данного слова в файле нет, то возвращается null
     * @param searchWord
     * @return
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        Set<Integer> result = null;
        Node node = root;
        for (char ch : searchWord.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(ch)) {
                result = null;
                break;
            } else {
                node = node.children.get(ch);
                result = node.indexes;
            }
        }
        return result;
    }

    /**
     * Загрузка данных из файла и построение индекса.
     * @param filename
     */
    public void loadFile(String filename) {
        try (
                FileReader fileReader = new FileReader(filename);
                BufferedReader reader = new BufferedReader(fileReader)) {
            int c;
            int index = 0;
            int wordIndex = 0;
            StringBuilder word = new StringBuilder();
            while ((c = reader.read()) != -1) {
                if ((char) c == ' ') {
                    if (!word.toString().isEmpty()) {
                        put(word.toString(), wordIndex);
                    }
                    wordIndex = index + 1;
                    word.setLength(0);
                } else {
                    word.append(String.format("%s", (char) c));
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
