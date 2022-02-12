package com.camackenzie.exvi.core.util

interface SelfSerializable {
    fun toJson(): String
    fun getUID(): String
}