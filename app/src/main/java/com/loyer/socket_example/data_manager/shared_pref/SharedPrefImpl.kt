package com.loyer.socket_example.data_manager.shared_pref

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPref {
    override fun saveToken(token: String) {
        val editor = sharedPref.edit()
        editor.putString(SharedPrefKeys.Token.key, token).apply()
    }

    override fun removeToken() {
        val editor = sharedPref.edit()
        editor.remove(SharedPrefKeys.Token.key).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString(SharedPrefKeys.Token.key, null)
    }
}