package com.rodrigoapolo.dictionmaster.service

import com.rodrigoapolo.dictionmaster.model.WordDefinition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("{language}/{word}")
    fun getWord(
        @Path("word") word: String, @Path("language") language: String = "en"
    ): Call<List<WordDefinition>>
}