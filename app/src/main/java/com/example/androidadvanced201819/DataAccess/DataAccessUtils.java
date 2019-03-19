package com.example.androidadvanced201819.DataAccess;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.androidadvanced201819.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class DataAccessUtils {

    public static void initDataSource(Context context) {
        List<Profile> profiles = new ArrayList<Profile>();
        Singleton.getInstance().setProfiles(profiles);
    }

    public static List<Profile> getDataSourceItemList(Context context) {
        return Singleton.getInstance().getProfiles();
    }

    public static void addItem(Profile profile, Context context) {
        Singleton.getInstance().getProfiles().add(profile);
    }

    public static void removeItem(Profile nota, Context context) {
        Singleton.getInstance().getProfiles().remove(nota);
    }

    public static Profile getItemByPosition(Context context, int positon) {
        Profile contByPos = Singleton.getInstance().getProfiles().get(positon);
        return contByPos;
    }

    //Metodi per scrivere nella shared preferences
    public static void writeOnSharedPreferences(Boolean access_boolean, Context context) {
        //dichiarazione shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("access_boolean", Context.MODE_PRIVATE);
        //dichiarazione edit dove scrivo la chiave e il valore corrispondente
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("access_boolean", access_boolean);
        editor.commit();
    }

    public static Boolean getOnSharedPreferences(Context context) {

        //recupero dell chiave
        SharedPreferences sharedPreferences = context.getSharedPreferences("access_boolean", Context.MODE_PRIVATE);
        Boolean accessBoolean = sharedPreferences.getBoolean("access_boolean", false);
        return accessBoolean;
    }
}
