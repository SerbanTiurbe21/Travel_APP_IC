package com.google.mytravelapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilitySharedPreferences {
    public static String getSharedPrefEmail(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        String defaultEmail = "defaultEmail";
        String email = sharedPref.getString("email", defaultEmail);
        return email;
    }

    public static String getSharedPrefUsername(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        String defaultName = "defaultName";
        String userName = sharedPref.getString("username", defaultName);
        return userName;
    }

    public static Integer getSharedPrefId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        Integer defaultId = 0;
        Integer id = sharedPref.getInt("id", defaultId);
        return id;
    }

    public static void applyEmailPreference(Context context, String userEmail){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", userEmail);
        editor.apply();
    }

    public static void applyUsernamePreference(Context context, String userName){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", userName);
        editor.apply();
    }

    public static void applyIdPreference(Context context, Integer id){
        SharedPreferences sharedPref = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", id);
        editor.apply();
    }
}
