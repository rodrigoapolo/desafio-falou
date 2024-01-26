package com.rodrigoapolo.dictionmaster.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.rodrigoapolo.dictionmaster.model.WordDefinition

class SecurityPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).apply()
    }

    fun storeInt(key: String, value: Int) {
        this.sharedPreferences.edit().putInt(key, value).apply()
    }

    fun storeLong(key: String, value: Long) {
        this.sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getStoredString(key: String): String {
        return this.sharedPreferences.getString(key, "") ?: ""
    }

    fun getStoredInt(key: String): Int {
        return this.sharedPreferences.getInt(key, 0)
    }

    fun getStoredLong(key: String): Long {
        return this.sharedPreferences.getLong(key, 0)
    }

    fun storeWordList(key: String, wordList: List<WordDefinition>) {
        val gson = Gson()
        val json = gson.toJson(wordList)
        this.sharedPreferences.edit().putString(key, json).apply()
    }

    fun getStoredWordList(key: String): List<WordDefinition> {
        val json = this.sharedPreferences.getString(key, null) ?: return emptyList()

        return try {
            val gson = Gson()
            val type = object : TypeToken<List<WordDefinition>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        }
    }


}
