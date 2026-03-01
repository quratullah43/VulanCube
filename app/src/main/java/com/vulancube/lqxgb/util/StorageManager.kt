package com.vulancube.lqxgb.util

import android.content.Context
import android.content.SharedPreferences

class StorageManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("access_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun saveLink(link: String) {
        prefs.edit().putString("content_link", link).apply()
    }

    fun getLink(): String? {
        return prefs.getString("content_link", null)
    }

    fun savePolicyLink(link: String) {
        prefs.edit().putString("policy_link", link).apply()
    }

    fun getPolicyLink(): String? {
        return prefs.getString("policy_link", null)
    }

    fun hasToken(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
