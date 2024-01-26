package com.rodrigoapolo.dictionmaster.retrofit

import android.util.Log
import com.rodrigoapolo.dictionmaster.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryApi {
    companion object {
        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder().build()

            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(http)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return INSTANCE
        }

        fun <S> createService(c: Class<S>): S {
            return getRetrofitInstance().create(c)
        }

    }
}