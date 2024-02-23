package com.rodrigoapolo.dictionmaster.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.dictionmaster.model.WordDefinition
import com.rodrigoapolo.dictionmaster.retrofit.DictionaryApi
import com.rodrigoapolo.dictionmaster.service.DictionaryApiService
import com.rodrigoapolo.dictionmaster.util.Constants
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    private val _erro = MutableLiveData<Boolean>()
    private val _wordDefinition = MutableLiveData<WordDefinition>()

    fun status(): LiveData<Boolean> {
        return _status
    }
    fun erro(): LiveData<Boolean> {
        return _erro
    }
    fun wordDefinition(): LiveData<WordDefinition> {
        return _wordDefinition
    }

    fun validateData(word: String) {
        _status.value = true
        if (word.length <= 0) _status.value = false
    }

    fun searchWord(word: String, context: Context) {
        var cacheWordSearch =
            SecurityPreferences(context).getStoredWordList(Constants.CACHE_WORD_SEARCH)
        var wordSearch = cacheWordSearch.find { it.word == word }

        if (wordSearch != null) {
            _wordDefinition.value = wordSearch!!
        } else {
            getWordApi(word)
        }
    }

    private fun getWordApi(word: String) {
        val service = DictionaryApi.createService(DictionaryApiService::class.java)
        val callback = service.getWord(word, "en")

        callback.enqueue(object : Callback<List<WordDefinition>> {
            override fun onResponse(
                call: Call<List<WordDefinition>>,
                response: Response<List<WordDefinition>>
            ) {
                if (response.isSuccessful) {
                    _wordDefinition.value = response.body()!![0]
                }else{
                    _erro.value = true
                }
            }

            override fun onFailure(call: Call<List<WordDefinition>>, t: Throwable) {
                Log.i("apiWord", "$call   $t")
            }
        })
    }
}