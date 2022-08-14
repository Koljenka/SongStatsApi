package de.knagel.songstats.model

import java.time.LocalDate
import java.util.*

@Suppress("unused") // is used by jackson to convert from json
data class TopTrackMonth(
    var count: Int,
    var trackId: String,
    var firstDate: LocalDate
) {
    constructor() : this(-1, "", LocalDate.now())
}
