package com.example.androidadvanced201819.DataAccess;

import com.example.androidadvanced201819.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private static Singleton ourInstance = new Singleton();
    private List profiles = new ArrayList<Profile>();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {

    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return this.profiles;
    }
}
