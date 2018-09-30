package ru.job4j.stat;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Store {
    public Info diff(List<User> previous, List<User> current) {

        Map<Integer, String> previousMap = previous.stream()
                .collect(Collectors.toMap(u -> u.id, u -> u.name));
        int added = (int) current.stream()
                .filter(user -> !previous.contains(user))
                .count();
        int changed = (int) current.stream()
                .filter(user -> previousMap.containsKey(user.id) && !previousMap.get(user.id).equals(user.name))
                .count();
        int deleted = (int) previous.stream()
                .filter(user -> !current.contains(user))
                .count();
        return new Info(added, changed, deleted);
    }

    static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
