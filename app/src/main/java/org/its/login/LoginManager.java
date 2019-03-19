package org.its.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginManager {

    private static final String LOGGED_IN_PREF = "logged_in_status";

    private static final String LOGGED_USER = "logged_user";



    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedStatus(Context context, boolean loggedIn, String user) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        if(loggedIn) {
            editor.putString(LOGGED_USER,user);
        }
        else
            editor.putString(LOGGED_USER,null);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static String getLoggedUser(Context context) {
        return getPreferences(context).getString(LOGGED_USER, "Nessun utente loggato");
    }
}
