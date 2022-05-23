/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualBodyStats
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
data class GetBodyStatsResponse(
    val bodyStats: ActualBodyStats
) : GenericDataResult()