package ru.job4j.utils.fs;

import com.google.common.base.Joiner;

import java.io.File;
import java.util.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Search {

    /**
     * Return list of files
     * uses recursion
     * @param parent searching directory
     * @param exts extensions of files
     * @return list of objects File
     */
    public List<File> files(String parent, List<String> exts) {
        ArrayList<File> result = new ArrayList<>();
        File rootFile = new File(parent);
        String matchString = ".*\\.[" + Joiner.on("|").join(exts) + "]+";
        for (File file : rootFile.listFiles()) {
            if (file.isDirectory()) {
                for (File f : this.files(file.getPath(), exts)) {
                    result.add(f);
                }
            } else {
                if (file.getName().matches(matchString)) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    /**
     * Return list of files
     * uses no recursion
     * @param parent searching directory
     * @param exts extensions of files
     * @return list of objects File
     */
    public List<File> filesNoRecursion(String parent, List<String> exts) {
        ArrayList<File> result = new ArrayList<>();
        LinkedList<File> directories = new LinkedList<>();
        directories.push(new File(parent));
        String matchString = ".*\\.[" + Joiner.on("|").join(exts) + "]+";
        while (directories.size() != 0) {
            for (File file : directories.pop().listFiles()) {
                if (file.isDirectory()) {
                    directories.offer(file);
                } else {
                    if (file.getName().matches(matchString)) {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }
}
