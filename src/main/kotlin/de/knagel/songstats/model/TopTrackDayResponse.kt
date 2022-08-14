package de.knagel.songstats.model

@Suppress("unused") // is used by jackson to convert from json
data class TopTrackDayResponse(
    var days: Int,
    var plays: Int
) {
    constructor() : this(0, 0)
}