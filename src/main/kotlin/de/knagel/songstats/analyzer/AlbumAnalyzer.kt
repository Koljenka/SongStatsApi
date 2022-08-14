package de.knagel.songstats.analyzer

import de.knagel.songstats.model.BoxStat
import de.knagel.songstats.model.StatRequest
import java.util.concurrent.TimeUnit

object AlbumAnalyzer {
    fun analyseAll(statRequest: StatRequest): List<BoxStat> =
        if (statRequest.album?.totalTracks ?: 0 < 2) emptyList()
        else listOfNotNull(
            analyseIsSongLongest(statRequest),
            analyseIsSongShortest(statRequest)
        )

    private fun analyseIsSongLongest(statRequest: StatRequest): BoxStat? {
        val (_, track, album) = statRequest
        if (album?.tracks?.maxByOrNull { t -> t.duration ?: 0 }?.id == track?.id) {
            return BoxStat(
                msToTime(track?.duration?.toLong() ?: 0),
                "No other song in ${album?.name} is as long as ${track?.name}",
                "access_time"
            )
        }
        return null
    }

    private fun analyseIsSongShortest(statRequest: StatRequest): BoxStat? {
        val (_, track, album) = statRequest
        if (album?.tracks?.minByOrNull { t -> t.duration ?: Int.MAX_VALUE }?.id == track?.id) {
            return BoxStat(
                msToTime(track?.duration?.toLong() ?: 0),
                "No other song in ${album?.name} is as short as ${track?.name}",
                "access_time"
            )
        }
        return null
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