package com.rodrigoapolo.dictionmaster.model

import java.io.Serializable

data class Definition(
    val definition: String?,
    val synonyms: List<String>?,
    val antonyms: List<String>?,
    val example: String?
)