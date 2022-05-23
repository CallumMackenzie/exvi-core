/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualBodyStats
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

/**
 * Response type: NoneResult
 */
@Serializable
@Suppress("unused")
data class SetBodyStatsRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val bodyStats: ActualBodyStats
) : GenericDataRequest(), ValidatedUserRequest {

    constructor(username: String, accessKey: String, bodyStats: ActualBodyStats)
            : this(username.cached(), accessKey.cached(), bodyStats)

    constructor(username: String, accessKey: EncodedStringCache, bodyStats: ActualBodyStats)
            : this(username.cached(), accessKey, bodyStats)

    constructor(username: EncodedStringCache, accessKey: String, bodyStats: ActualBodyStats)
            : this(username, accessKey.cached(), bodyStats)
}