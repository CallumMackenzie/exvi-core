package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.json.*
import kotlinx.serialization.*

@kotlinx.serialization.Serializable
class DeleteWorkoutsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workoutIds: Array<EncodedStringCache>
) : GenericDataRequest(uid) {

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    companion object {
        const val uid = "DeleteWorkoutsRequest"
    }
}