package de.knagel.songstatsapi.model

import java.time.Duration
import java.util.*

data class TimeFrame(
    val start: Long,
    val end: Long
) {
    fun startDate(): Date = Date(start)
    fun endDate(): Date = Date(end)
    fun toDuration(): Duration = Duration.ofMillis(end - start)
    override fun toString(): String {
        return "TimeFrame(from=$start(${startDate()}), to=$end(${endDate()}))"
    }
}
