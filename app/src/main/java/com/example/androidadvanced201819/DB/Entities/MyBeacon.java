package com.example.androidadvanced201819.DB.Entities;

import org.altbeacon.beacon.Beacon;

public class MyBeacon extends Beacon {
    private Integer id;
    private String name;
    private String address;
    private Integer profileId;

    public MyBeacon(int id, String nome, String address, int idProfilo) {
    this.id=id;
    this.name=nome;
    this.address=address;
    this.profileId=idProfilo;
    }

    public MyBeacon(String nome, String address) {
        this.name=nome;
        this.address=address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
