package de.knagel.songstatsapi.analyser

import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.requests.StatRequest
import java.util.concurrent.TimeUnit

object AlbumAnalyser {

    fun analyseAll(statRequest: StatRequest): List<BoxStat> {
        return listOfNotNull(
            analyseIsSongLongest(statRequest),
            analyseIsSongShortest(statRequest)
        )
    }


    private val analyseIsSongLongest = StatAnalyser { (_, track, album) ->
        if (album.tracks.maxByOrNull { t -> t.duration }?.id == track.id) {
            return@StatAnalyser BoxStat(
                msToTime(track.duration.toLong()),
                "No other Song in the Album '${album.name}' is as long as '${track.name}'",
                "access_time"
            )
        }
        return@StatAnalyser null
    }

    private val analyseIsSongShortest = StatAnalyser { (_, track, album) ->
        if (album.tracks.minByOrNull { t -> t.duration }?.id == track.id) {
           return@StatAnalyser BoxStat(
                msToTime(track.duration.toLong()),
                "No other Song in the Album '${album.name}' is as short as '${track.name}'",
                "access_time"
            )
        }
       return@StatAnalyser null
    }

    private fun msToTime(millis: Long): String {
        return String.format(
            "%2d Minutes and %2d Seconds",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }
}