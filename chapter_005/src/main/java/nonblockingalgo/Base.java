package nonblockingalgo;

import java.io.Serializable;

public class Base implements Serializable {
    private String data;
    private int version;
    private Integer id;

    public Base(String data, int id) {
        this.data = data;
        this.id = id;
        this.version = 1;
    }

    public void edit(String data) {
        this.data = data;
        version++;
    }

    public String getData() {
        return data;
    }

    public int getVersion() {
        return version;
    }

    public Integer getId() {
        return id;
    }
}
