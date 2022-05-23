/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache

interface ValidatedUserRequest {
    val username: EncodedStringCache
    val accessKey: EncodedStringCache
}