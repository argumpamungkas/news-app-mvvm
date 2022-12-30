package com.argumelar.newsapp.network.model

import android.content.Context
import android.content.SharedPreferences

class PreferencesLogin(var context: Context) {

    private val PREF_NAME = "login_user"
    private val sharedPref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun putToken(key: String, value: String){
        editor.putString(key, value)
            .apply()
    }

    fun getToken(key: String ): String? {
        return sharedPref.getString(key, null)
    }

    fun putLogin(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getLogin(key: String) : Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }
}