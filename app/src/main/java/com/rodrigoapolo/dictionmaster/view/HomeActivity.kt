package com.rodrigoapolo.dictionmaster.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.dictionmaster.databinding.ActivityHomeBinding
import com.rodrigoapolo.dictionmaster.util.Constants
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import com.rodrigoapolo.dictionmaster.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        createListenerData()
        setObserver()

        setContentView(binding.root)
    }

    private fun setObserver() {
        viewModel.status().observe(this) {
            if (it) binding.bntSearch.visibility = View.VISIBLE else binding.bntSearch.visibility =
                View.INVISIBLE

        }
    }

    private fun createListenerData() {
        binding.ediSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateData(binding.ediSearch.text.toString())
            }
        })

        binding.bntSearch.setOnClickListener {
            SecurityPreferences(this).storeString(Constants.WORD, binding.ediSearch.text.toString())
            startWordDefinition()

        }
    }

    private fun startWordDefinition() {
        val intent = Intent(this, WordDefinitionActivity::class.java)
        startActivity(intent)
        finish()
    }
}