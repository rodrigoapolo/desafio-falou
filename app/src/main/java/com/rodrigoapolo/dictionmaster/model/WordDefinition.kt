package com.rodrigoapolo.dictionmaster.model

data class WordDefinition(
    val word: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val license: License,
    val sourceUrls: List<String>
)
