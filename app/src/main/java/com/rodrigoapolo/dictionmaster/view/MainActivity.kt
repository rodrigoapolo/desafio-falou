package com.rodrigoapolo.dictionmaster.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.rodrigoapolo.dictionmaster.BuildConfig
import com.rodrigoapolo.dictionmaster.R
import com.rodrigoapolo.dictionmaster.databinding.ActivityMainBinding
import com.rodrigoapolo.dictionmaster.retrofit.DictionaryApi
import com.rodrigoapolo.dictionmaster.util.Constants
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}