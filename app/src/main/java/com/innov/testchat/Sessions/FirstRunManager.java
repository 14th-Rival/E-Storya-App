package com.innov.testchat.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstRunManager {

    private SharedPreferences mPreferences_intro;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private final int PRIVATE_MODE = 0;

    private static final String pref_name = "first_run";
    private static final String pref_boolean = "first_time";

    public FirstRunManager(Context mContext) {
        this.mContext = mContext;
        this.mEditor = mPreferences_intro.edit();
        this.mPreferences_intro = mContext.getSharedPreferences(pref_name, PRIVATE_MODE);
    }

    public boolean checkFirst(){
        return mPreferences_intro.getBoolean(pref_boolean, false);
    }

    public void firstRun(Boolean b){
        mEditor.putBoolean(pref_boolean, true);
        mEditor.commit();
    }

    public void clearRun(Boolean inAppClose){
        mEditor.clear();
        mEditor.commit();
    }
}
