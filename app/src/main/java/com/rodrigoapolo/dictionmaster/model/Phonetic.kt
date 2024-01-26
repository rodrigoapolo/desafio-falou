package com.rodrigoapolo.dictionmaster.model

data class Phonetic(
    val text: String,
    val audio: String,
    val sourceUrl: String,
    val license: License
)