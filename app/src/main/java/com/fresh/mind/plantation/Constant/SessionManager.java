package com.fresh.mind.plantation.Constant;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    Editor editor;
    FragmentActivity context;
    int PRIVATE_MODE = 0;
    public static String mCurrentClass = "normal";
    private static String PREF_NAME = "Session";
    public static String PREF_LANGUAGE = "-1";
    private static String IS_LOGIN = "IsLoggedIn";
    public static String KEY_NO = "";

    public SessionManager(FragmentActivity context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NO, email);
        editor.commit();
        Log.d("SessionCreate", "" + email);
    }

    public boolean checkLogin() {
        if (!this.isLoggedIn()) {
            Log.d("asa", "ffff");
          /*  context.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LoginPage()).commit();*/
            return false;
        } else {
            Log.d("asa", "tttt");
          /*  context.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new Home2()).commit();*/
            return true;
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("email", pref.getString(KEY_NO, KEY_NO));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        /*LoginPage loginPage = new LoginPage();
        Bundle bundle = new Bundle();
        bundle.putString("activity", "session");
        loginPage.setArguments(bundle);
        context.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, loginPage).commit();*/
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
