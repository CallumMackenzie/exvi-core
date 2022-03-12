package com.camackenzie.exvi.core.util

import kotlinx.datetime.Clock
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

interface Identifiable : Comparable<Identifiable> {
    fun getIdentifier(): EncodedStringCache

    fun toRawIdentifiable(): RawIdentifiable = RawIdentifiable(getIdentifier())

    override fun compareTo(other: Identifiable): Int = IdentifiableComparator.compare(this, other)

    companion object {
        @JvmStatic
        fun generateId(): EncodedStringCache = StringBuilder()
            .append(CryptographyUtils.generateSalt(16))
            .append(CryptographyUtils.hashSHA256(Clock.System.now().epochSeconds.toString(16)))
            .toString().cached()

        /**
         * Calls onIntersect for every element present in a and b -> onIntersect(a, aIndex, b, bIndex)
         * Calls onAOnly for every element in a that is not in b -> onAOnly(a, aIndex)
         * Calls onBOnly for every element in b that is not in a -> onBOnly(b, bIndex)
         */
        @JvmStatic
        @JvmOverloads
        fun checkIntersects(
            a: List<Identifiable>,
            b: List<Identifiable>,
            onIntersect: (Identifiable, Int, Identifiable, Int) -> Unit = { _, _, _, _ -> },
            onAOnly: (Identifiable, Int) -> Unit = { _, _ -> },
            onBOnly: (Identifiable, Int) -> Unit = { _, _ -> }
        ) {
            val bSorted = b.mapIndexed { index, obj ->
                IndexPreserver(obj, index)
            }.sorted()
            val bsMatched = BooleanArray(b.size)
            for (i in a.indices) {
                val identifiable = IndexPreserver(a[i], i)
                val bIndex = bSorted.binarySearch(identifiable)
                if (bIndex < 0) onAOnly(identifiable.data, i)
                else {
                    val preserved = bSorted[bIndex]
                    bsMatched[preserved.index] = true
                    onIntersect(identifiable.data, i, b[preserved.index], preserved.index)
                }
            }
            for (i in bsMatched.indices) {
                if (!bsMatched[i]) onBOnly(b[i], i)
            }
        }

        object IdentifiableComparator : Comparator<Identifiable> {
            override fun compare(a: Identifiable, b: Identifiable): Int =
                a.getIdentifier().compareTo(b.getIdentifier())
        }
    }
}

data class IndexPreserver<T : Comparable<T>>(
    val data: T,
    val index: Int
) : Comparable<IndexPreserver<T>> {
    override fun compareTo(other: IndexPreserver<T>): Int = data.compareTo(other.data)
    override fun equals(other: Any?): Boolean = if (other !is IndexPreserver<*>) false
    else other.data == this.data
}

data class RawIdentifiable(val id: EncodedStringCache) : Identifiable {
    constructor(id: String) : this(id.cached())

    override fun getIdentifier(): EncodedStringCache = id
}