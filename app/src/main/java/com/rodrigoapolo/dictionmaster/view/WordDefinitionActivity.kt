package com.rodrigoapolo.dictionmaster.view

import android.content.Intent
import android.icu.util.LocaleData
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.dictionmaster.databinding.ActivityWordDefinitionBinding
import com.rodrigoapolo.dictionmaster.util.Constants
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import com.rodrigoapolo.dictionmaster.viewmodel.WordDefinitionViewModel
import java.io.IOException
import java.time.LocalDate

class WordDefinitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordDefinitionBinding
    private lateinit var viewModel: WordDefinitionViewModel
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordDefinitionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WordDefinitionViewModel::class.java]
        mediaPlayer = MediaPlayer()


        createListenerData()
        setObserver()

        setContentView(binding.root)
    }

    private fun setObserver() {
        viewModel.wordDefinition().observe(this) { word ->
            binding.texWord.text = word.word.capitalize()
            binding.texPhonetic.text = word.phonetic
            binding.texDefinition.text = word.meanings[0].definitions[0].definition
            binding.texExample.text = word.meanings[0].definitions[0].example
            binding.texForWord.text = "${binding.texForWord.text.toString()} \"${word.word}\""

            if (!word.phonetics[0].audio.isNullOrBlank()) {
                mediaPlayer.setDataSource(word.phonetics[0].audio)
                mediaPlayer.prepareAsync()
            }

            var cacheWordSearch =
                SecurityPreferences(this).getStoredWordList(Constants.CACHE_WORD_SEARCH)
            cacheWordSearch = cacheWordSearch.toMutableList().apply {
                if (!contains(word)) {
                    add(word)
                }
            }
            SecurityPreferences(this).storeWordList(Constants.CACHE_WORD_SEARCH, cacheWordSearch)
        }

        viewModel.erro().observe(this) {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun createListenerData() {
        var word = SecurityPreferences(this).getStoredString(Constants.WORD)

        viewModel.searchWord(word, this)

        binding.bntNewSearch.setOnClickListener {
            var date = SecurityPreferences(this).getStoredString(Constants.DATA)
            var cacheWordSearch =
                SecurityPreferences(this).getStoredWordList(Constants.CACHE_WORD_SEARCH)

            val dateNow = LocalDate.now()

            if (cacheWordSearch.size >= 10) {
                if (dateNow.toString() == date) {
                    startPurchase()
                } else {
                    SecurityPreferences(this).storeString(Constants.DATA, dateNow.toString())
                    SecurityPreferences(this).storeWordList(Constants.CACHE_WORD_SEARCH, listOf())
                    startHome()
                }
            } else {
                SecurityPreferences(this).storeString(Constants.DATA, dateNow.toString())
                startHome()
            }
        }

        binding.imgAudio.setOnClickListener {
            mediaPlayer.start()
        }

        binding.imgBackgrou.setOnClickListener {
            mediaPlayer.start()
        }
    }

    private fun startPurchase() {
        val intent = Intent(this, PurchaseActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}