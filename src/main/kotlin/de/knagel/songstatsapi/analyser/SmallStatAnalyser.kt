package de.knagel.songstatsapi.analyser

import de.knagel.songstatsapi.model.PlaybackHistoryObject
import de.knagel.songstatsapi.model.SmallStat
import de.knagel.songstatsapi.model.requests.SmallStatRequest

object SmallStatAnalyser {

    fun analyseAll(smallStatRequest: SmallStatRequest): List<SmallStat> {

        return listOfNotNull(
                getTotalTracks(smallStatRequest)
        )
    }

    fun getTotalTracks(req: SmallStatRequest): SmallStat {
        println(req.playbackHistory[0])
        return SmallStat(0, "Test", "music_note", req.prevTimeframe, "200", "300");
    }
}