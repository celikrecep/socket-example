package com.loyer.socket_example.data_manager.shared_pref

interface SharedPref {
    fun saveToken(token: String)
    fun removeToken()
    fun getToken(): String?
}