package org.its.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Nfc implements Serializable {
    private String id;
    private List<String> techList = new ArrayList<>();

    public Nfc() {
    }

    public Nfc(String id, List<String> techList) {
        this.id = id;
        this.techList = techList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTechList() {
        return techList;
    }

    public void setTechList(List<String> techList) {
        this.techList = techList;
    }
}
