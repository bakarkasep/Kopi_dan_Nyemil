package com.kopinyemil.app.util

import android.content.Context
import com.kopinyemil.app.model.User

/** Penyimpanan lokal sederhana (SharedPreferences), tanpa database. */
class PreferencesManager(context: Context) {
    private val prefs = context.getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)

    fun registerAccount(username: String, password: String, name: String) {
        prefs.edit().putString(accountKey(username), "$password\n$name").apply()
    }

    fun accountExists(username: String): Boolean = prefs.contains(accountKey(username))

    /** Mengembalikan User jika kredensial cocok, selain itu null. */
    fun authenticate(username: String, password: String): User? {
        val parts = prefs.getString(accountKey(username), null)?.split("\n", limit = 2) ?: return null
        if (parts.size < 2 || parts[0] != password) return null
        return User(username.trim(), parts[1])
    }

    fun saveSession(user: User) {
        prefs.edit().putString(KEY_USERNAME, user.username).putString(KEY_NAME, user.name).apply()
    }

    fun getSession(): User? {
        val username = prefs.getString(KEY_USERNAME, null) ?: return null
        return User(username, prefs.getString(KEY_NAME, username) ?: username)
    }

    fun updateName(username: String, newName: String) {
        val edit = prefs.edit().putString(KEY_NAME, newName)
        val stored = prefs.getString(accountKey(username), null)?.split("\n", limit = 2)
        if (stored != null && stored.size == 2) {
            edit.putString(accountKey(username), "${stored[0]}\n$newName")
        }
        edit.apply()
    }

    fun clearSession() {
        prefs.edit().remove(KEY_USERNAME).remove(KEY_NAME).apply()
    }

    private fun accountKey(username: String) = "acc_" + username.trim().lowercase()

    private companion object {
        const val KEY_USERNAME = "session_username"
        const val KEY_NAME = "session_name"
    }
}
