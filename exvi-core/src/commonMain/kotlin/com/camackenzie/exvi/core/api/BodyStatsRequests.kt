package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.BodyStats
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Suppress("unused")
@Serializable
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

@Suppress("unused")
@Serializable
class GetBodyStatsResponse(
    val bodyStats: BodyStats
) : GenericDataResult(uid) {

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "GetBodyStatsResponse"
    }
}

@Suppress("unused")
@Serializable
class SetBodyStatsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val bodyStats: BodyStats
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, bodyStats: BodyStats)
            : this(username.cached(), accessKey.cached(), bodyStats)

    constructor(username: String, accessKey: EncodedStringCache, bodyStats: BodyStats)
            : this(username.cached(), accessKey, bodyStats)

    constructor(username: EncodedStringCache, accessKey: String, bodyStats: BodyStats)
            : this(username, accessKey.cached(), bodyStats)

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "SetBodyStatsRequest"
    }
}