package com.rodrigoapolo.dictionmaster.model

import java.io.Serializable

data class Meaning(
    val partOfSpeech: String?,
    val definitions: List<Definition>?,
    val synonyms: List<String>?,
    val antonyms: List<String>?
)