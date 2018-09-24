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

    public List<Org> sort(List<Org> orgs) {
        Comparator<Org> deptComparator = (org1, org2) -> {
            int min = Math.min(org1.length(), org2.length());
            int result = 0;
            for (int i = 0; i < min; i++) {
                if (!org1.get(i).equals(org2.get(i))) {
                    result = org1.get(i).compareTo(org2.get(i));
                    break;
                }
            }
            if (result == 0) {
                result = (org1.length() - org2.length());
            }
            return result;
        };
        orgs = appendAllNodes(orgs);
        orgs.sort(deptComparator);
        return orgs;
    }

    protected List<Org> appendAllNodes(List<Org> orgs) {
        Set<Org> result = new HashSet<>();
        for (Org org : orgs) {
            StringBuilder path = new StringBuilder();
            for (String s : org.getDepts()) {
                if (path.length() != 0) {
                   path.append("\\");
                }
                path.append(s);
                result.add(new Org(path.toString()));
            }
        }
        List<Org> list = new ArrayList<>(result);
        return list;
    }
}
