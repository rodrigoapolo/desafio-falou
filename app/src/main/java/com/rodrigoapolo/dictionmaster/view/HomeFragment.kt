package com.rodrigoapolo.dictionmaster.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rodrigoapolo.dictionmaster.R
import com.rodrigoapolo.dictionmaster.databinding.FragmentHomeBinding
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import com.rodrigoapolo.dictionmaster.viewmodel.HomeViewModel
import kotlin.reflect.typeOf

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createListenerData()
        setObserver()
    }

    private fun setObserver() {
        viewModel.status().observe(viewLifecycleOwner) {
            if (it) binding.bntSearch.visibility = View.VISIBLE else binding.bntSearch.visibility =
                View.INVISIBLE
        }

        viewModel.wordDefinition().observe(viewLifecycleOwner){
            val action = HomeFragmentDirections.actionHomeFragmentToWordDefinitionFragment(it)
            findNavController().navigate(action)
        }

        viewModel.erro().observe(viewLifecycleOwner){
            if (it){
                binding.bntSearch.text = "search"
                binding.loadingProgressBar.visibility = View.GONE

                Toast.makeText(requireContext(), "Word not found", Toast.LENGTH_LONG).show()
            }
        }

        if (args.iskeyboard){
            binding.ediSearch.requestFocus()
            openKeyboard(binding.ediSearch)
        }


    }

    private fun createListenerData() {
        binding.ediSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ediSearch.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_condensed_bold)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateData(binding.ediSearch.text.toString())
                if (binding.ediSearch.text.toString().length == 0){
                    binding.ediSearch.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_condensed_light)
                }
            }
        })


        binding.bntSearch.setOnClickListener {
            binding.bntSearch.text = ""
            binding.loadingProgressBar.visibility = View.VISIBLE
            viewModel.searchWord(binding.ediSearch.text.toString(), requireContext())
        }

        binding.ediSearch.setOnFocusChangeListener { view, b ->
            if (!b){
                binding.ediSearch.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_condensed_light)
            }
        }
    }

    private fun openKeyboard(editText: EditText) {
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}