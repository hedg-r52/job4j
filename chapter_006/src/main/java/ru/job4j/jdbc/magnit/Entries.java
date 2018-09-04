package ru.job4j.jdbc.magnit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Entries {
    List<Entry> entries;

    public Entries() {
        this.entries = new ArrayList<>();
    }

    @XmlElement(name = "entry")
    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void add(Entry entry) {
        this.entries.add(entry);
    }

    public void remove(int id) {
        this.entries.remove(id);
    }
}
