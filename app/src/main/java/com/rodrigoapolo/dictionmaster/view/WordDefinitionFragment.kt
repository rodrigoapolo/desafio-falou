package com.rodrigoapolo.dictionmaster.view

import android.media.MediaPlayer
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rodrigoapolo.dictionmaster.R
import com.rodrigoapolo.dictionmaster.databinding.FragmentWordDefinitionBinding
import com.rodrigoapolo.dictionmaster.model.Meaning
import com.rodrigoapolo.dictionmaster.util.Constants
import com.rodrigoapolo.dictionmaster.util.SecurityPreferences
import com.rodrigoapolo.dictionmaster.viewmodel.WordDefinitionViewModel
import java.time.LocalDate

class WordDefinitionFragment : Fragment(R.layout.fragment_word_definition) {

    private lateinit var binding: FragmentWordDefinitionBinding
    private lateinit var viewModel: WordDefinitionViewModel
    private lateinit var mediaPlayer: MediaPlayer
    private val args: WordDefinitionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDefinitionBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WordDefinitionViewModel::class.java]
        mediaPlayer = MediaPlayer()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createListenerData()
        setObserver()
    }

    private fun setObserver() {

        setWordView()
    }

    private fun setWordView() {
        val wordDefinition = args.WordDefinition
        binding.texWord.text = wordDefinition.word?.capitalize() ?: ""
        binding.texPhonetic.text = wordDefinition.phonetic


        binding.texDefinition1.text =  formtDefinition(wordDefinition.meanings, 0)
        binding.texExample1.text = formtExample(wordDefinition.meanings, 0)

        if (wordDefinition.meanings?.size ?: 0 > 1) {
            binding.texDefinition2.text = formtDefinition(wordDefinition.meanings, 1)
            binding.texExample2.text = formtExample(wordDefinition.meanings, 1)
        }
        if (wordDefinition.meanings?.size ?: 0 > 2) {
            binding.texDefinition3.text = formtDefinition(wordDefinition.meanings, 2)
            binding.texExample3.text = formtExample(wordDefinition.meanings, 2)
        }


        binding.texForWord.text = "${binding.texForWord.text.toString()} \"${wordDefinition.word}\""

        if (!wordDefinition.phonetics?.get(0)?.audio.isNullOrBlank()) {
            mediaPlayer.setDataSource(wordDefinition.phonetics!![0].audio)
            mediaPlayer.prepareAsync()

            binding.imgAudio.visibility = View.GONE
            binding.loadingProgressBar.visibility = View.VISIBLE

            mediaPlayer.setOnPreparedListener {
                binding.imgAudio.visibility = View.VISIBLE
                binding.loadingProgressBar.visibility = View.GONE
            }
        } else {
            binding.imgAudio.visibility = View.INVISIBLE
            binding.imgBackgrou.visibility = View.INVISIBLE
        }


        var cacheWordSearch =
            SecurityPreferences(requireContext()).getStoredWordList(Constants.CACHE_WORD_SEARCH)
        cacheWordSearch = cacheWordSearch.toMutableList().apply {
            if (!contains(wordDefinition)) {
                add(wordDefinition)
            }
        }
        SecurityPreferences(requireContext()).storeWordList(
            Constants.CACHE_WORD_SEARCH,
            cacheWordSearch
        )
    }

    private fun formtExample(meanings: List<Meaning>?, position: Int): SpannableString {
        val synonym = meanings?.get(position)?.definitions?.get(0)?.synonyms
        if (synonym.isNullOrEmpty())
            return SpannableString(HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY))
        val example = buildString {
            append("<ul>")
            synonym?.forEach { synonym ->
                append(" <li>$synonym</li>")
            }
            append("</ul>")
        }
        return SpannableString(HtmlCompat.fromHtml(example, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }

    private fun formtDefinition(meanings: List<Meaning>?, position: Int): SpannableString {
        val partOf = meanings?.get(position)?.partOfSpeech
        val definition = meanings?.get(position)?.definitions?.get(0)?.definition ?: ""
        if (definition.isNullOrEmpty())
            return SpannableString(HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY))
        val txt =
            """${position + 1}) <font color='#052D3980'>[$partOf]</font> $definition""".trimIndent()
        return SpannableString(HtmlCompat.fromHtml(txt, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }

    private fun createListenerData() {
        binding.bntNewSearch.setOnClickListener {
            var date = SecurityPreferences(requireContext()).getStoredString(Constants.DATA)
            var cacheWordSearch =
                SecurityPreferences(requireContext()).getStoredWordList(Constants.CACHE_WORD_SEARCH)

            val dateNow = LocalDate.now()

            if (cacheWordSearch.size >= 100) {
                if (dateNow.toString() == date) {
                    startPurchase()
                } else {
                    SecurityPreferences(requireContext()).storeString(Constants.DATA, dateNow.toString())
                    SecurityPreferences(requireContext()).storeWordList(Constants.CACHE_WORD_SEARCH, listOf())
                    startHome()
                }
            } else {
                SecurityPreferences(requireContext()).storeString(Constants.DATA, dateNow.toString())
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
        findNavController().navigate(R.id.action_wordDefinitionFragment_to_purchaseFragment)
    }

    private fun startHome() {
        val action = WordDefinitionFragmentDirections.actionWordDefinitionFragmentToHomeFragment(true)
        findNavController().navigate(action)
    }
}