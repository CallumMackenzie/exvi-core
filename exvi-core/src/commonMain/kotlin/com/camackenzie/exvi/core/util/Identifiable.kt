package com.camackenzie.exvi.core.util

import kotlinx.datetime.Clock

interface Identifiable {
    fun getIdentifier(): EncodedStringCache

    companion object {
        fun generateId(): EncodedStringCache = StringBuilder()
            .append(CryptographyUtils.generateSalt(16))
            .append(CryptographyUtils.hashSHA256(Clock.System.now().epochSeconds.toString(16)))
            .toString().cached()
    }
}