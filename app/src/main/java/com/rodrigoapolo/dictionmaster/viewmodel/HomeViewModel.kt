package com.rodrigoapolo.dictionmaster.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.dictionmaster.model.WordDefinition
import com.rodrigoapolo.dictionmaster.retrofit.DictionaryApi
import com.rodrigoapolo.dictionmaster.service.DictionaryApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()

    fun status(): LiveData<Boolean> {
        return _status
    }

    fun validateData(word: String) {
        _status.value = true
        if (word.length <= 0) _status.value = false
    }
}