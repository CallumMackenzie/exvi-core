package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualBodyStats
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable

data class GetBodyStatsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache
) : GenericDataRequest() {
    constructor(username: String, accessKey: String) : this(username.cached(), accessKey.cached())
    constructor(username: String, accessKey: EncodedStringCache) : this(username.cached(), accessKey)
    constructor(username: EncodedStringCache, accessKey: String) : this(username, accessKey.cached())
}

@Suppress("unused")
@Serializable
data class GetBodyStatsResponse(
    val bodyStats: ActualBodyStats
) : GenericDataResult()

@Suppress("unused")
@Serializable
data class SetBodyStatsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val bodyStats: ActualBodyStats
) : GenericDataRequest() {

    constructor(username: String, accessKey: String, bodyStats: ActualBodyStats)
            : this(username.cached(), accessKey.cached(), bodyStats)

    constructor(username: String, accessKey: EncodedStringCache, bodyStats: ActualBodyStats)
            : this(username.cached(), accessKey, bodyStats)

    constructor(username: EncodedStringCache, accessKey: String, bodyStats: ActualBodyStats)
            : this(username, accessKey.cached(), bodyStats)
}