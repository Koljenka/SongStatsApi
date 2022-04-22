package de.knagel.songstatsapi.model

import java.util.*

data class TimeFrame(
        val from: Long,
        val to: Long
) {
    val fromDate: Date = Date(from)
    val toDate: Date = Date(to)
}
