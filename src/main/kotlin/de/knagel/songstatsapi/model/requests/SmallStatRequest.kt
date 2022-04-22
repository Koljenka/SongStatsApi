package de.knagel.songstatsapi.model.requests

import de.knagel.songstatsapi.model.PlaybackHistoryObject
import de.knagel.songstatsapi.model.TimeFrame

data class SmallStatRequest(
    val accessToken: String,
    val playbackHistory: List<PlaybackHistoryObject>,
    val timeframe: TimeFrame,
    val prevTimeframe: TimeFrame
) {
    override fun toString(): String {
        return "SmallStatRequest(\n" +
                "timeframe=$timeframe, prevTimeframe=$prevTimeframe, \n" +
                "playbackHistory=(Length=${playbackHistory.size}, \n" +
                "[0]=${playbackHistory[0]}))"
    }
}
