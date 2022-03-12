package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.BodyStats
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class GetBodyStatsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String) : this(username.cached(), accessKey.cached())

    constructor(username: String, accessKey: EncodedStringCache) : this(username.cached(), accessKey)

    constructor(username: EncodedStringCache, accessKey: String) : this(username, accessKey.cached())

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "GetBodyStatsRequest"
    }
}

class GetBodyStatsResponse(
    val bodyStats: BodyStats
) : GenericDataResult(uid) {

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "GetBodyStatsResponse"
    }
}