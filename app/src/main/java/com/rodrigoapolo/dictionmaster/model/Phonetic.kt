package com.rodrigoapolo.dictionmaster.model

import java.io.Serializable

data class Phonetic(
    val text: String?,
    val audio: String?,
    val sourceUrl: String?,
    val license: License?
)