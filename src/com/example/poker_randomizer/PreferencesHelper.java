package com.example.poker_randomizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesHelper {
	private static String prefFileName="pref";
	private SharedPreferences preferences;
	private Editor editor;
	
	public PreferencesHelper(Context context){
		this.preferences=context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
		this.editor=preferences.edit();
		
	}
	public String getPreferences(String key){
		return preferences.getString(key, "");
	}
	public void savePreferences(String key,String value){
		editor.putString(key,value);
		editor.commit();
		
	}
}
