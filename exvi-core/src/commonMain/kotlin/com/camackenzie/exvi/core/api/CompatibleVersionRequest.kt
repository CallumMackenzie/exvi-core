/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class CompatibleVersionRequest(
    val version: Int
) : GenericDataRequest()

