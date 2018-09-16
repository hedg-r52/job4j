package ru.job4j.jdbc.tracker;

import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String desc;
    private long created;

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Item(String name, String desc, long created) {
        this(name, desc);
        this.created = created;
    }

    public Item(int id, String name, String desc, long created) {
        this(name, desc, created);
        this.id = id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public long getCreated() {
        return this.created;
    }

    @Override
    public String toString() {
        return "ID : " +
                this.getId() +
                "  Заявка: " +
                this.getName() +
                System.lineSeparator() +
                "Описание : " +
                this.getDesc() +
                System.lineSeparator() +
                "------------" +
                System.lineSeparator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && created == item.created
                && Objects.equals(name, item.name)
                && Objects.equals(desc, item.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, created);
    }
}
