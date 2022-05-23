package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

/**
 * Response type: GetBodyStatsResponse
 */
@Suppress("unused")
@Serializable
data class GetBodyStatsRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache
) : GenericDataRequest() ,ValidatedUserRequest {
    constructor(username: String, accessKey: String) : this(username.cached(), accessKey.cached())
    constructor(username: String, accessKey: EncodedStringCache) : this(username.cached(), accessKey)
    constructor(username: EncodedStringCache, accessKey: String) : this(username, accessKey.cached())
}

