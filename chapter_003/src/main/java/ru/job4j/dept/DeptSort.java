package ru.job4j.dept;

import java.util.*;

/**
 * Сортировка департаментов
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DeptSort {
    private static final String SEPARATOR = "\\\\";
    public List<String> sort(List<String> depts) {
        Comparator<String> deptComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] arr1 = o1.split(SEPARATOR);
                String[] arr2 = o2.split(SEPARATOR);
                int min = Math.min(arr1.length, arr2.length);
                int result = 0;
                for (int i = 0; i < min; i++) {
                    if (!arr2[i].equals(arr1[i])) {
                        result = arr1[i].compareTo(arr2[i]);
                        break;
                    }
                }
                if (result == 0) {
                    result = (arr1.length - arr2.length);
                }
                return result;
            }
        };
        depts = appendAllNodes(depts);
        Collections.sort(depts, deptComparator);
        return depts;
    }

    protected List<String> appendAllNodes(List<String> depts) {
        Set<String> result = new HashSet<>();
        for (String s : depts) {
            String[] array = s.split(SEPARATOR);
            StringBuilder path = new StringBuilder();
            for (String dept : array) {
                if (path.length() != 0) {
                    path.append("\\");
                }
                path.append(dept);
                result.add(path.toString());
            }
        }
        List<String> list = new ArrayList<>();
        list.addAll(result);
        return list;
    }
}
