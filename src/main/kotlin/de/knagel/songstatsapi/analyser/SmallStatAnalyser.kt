package de.knagel.songstatsapi.analyser

import de.knagel.songstatsapi.model.PlaybackHistoryObject
import de.knagel.songstatsapi.model.SmallStat
import de.knagel.songstatsapi.model.Stat
import de.knagel.songstatsapi.model.TimeFrame
import de.knagel.songstatsapi.model.requests.SmallStatRequest
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


object SmallStatAnalyser {

    fun analyseAll(smallStatRequest: SmallStatRequest): List<SmallStat> {
        val pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val future = pool.invokeAll(listOf(
            Callable { getTotalTracksStat(smallStatRequest) },
            Callable { getUniqueTracksStat(smallStatRequest) },
            Callable { getUniqueArtistStat(smallStatRequest) },
            Callable { getMostActiveDayStat(smallStatRequest) },
            Callable { getAverageHappinessStat(smallStatRequest) },
            Callable { getAverageEnergyStat(smallStatRequest) },
            Callable { getAverageDanceabilityStat(smallStatRequest) },
        ))
        val future2 = pool.submit(Callable { getListeningTimeAndTimeSpentStat(smallStatRequest) })

        val result = future.map { it.get() }.toMutableList()
        result.addAll(future2.get())

        return result
    }

    private fun getTotalTracksStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val value = filterHistoryForTimeframe(playbackHistory, timeframe).size.toString()
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue = filterHistoryForTimeframe(playbackHistory, prevTimeframe).size.toString()
        }

        return SmallStat(
            0, "Total Tracks", "music_note",
            Stat(smallStatRequest.prevTimeframe, "vs. $prevValue", value)
        )
    }


    private fun getUniqueTracksStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val value = filterHistoryForTimeframe(playbackHistory, timeframe).distinctBy { it.track.id }.size.toString()
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue =
                filterHistoryForTimeframe(playbackHistory, prevTimeframe).distinctBy { it.track.id }.size.toString()
        }
        return SmallStat(
            1, "Unique Tracks", "music_note",
            Stat(smallStatRequest.prevTimeframe, "vs. $prevValue", value)
        )
    }

    private fun getUniqueArtistStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val value =
            filterHistoryForTimeframe(playbackHistory, timeframe).distinctBy { it.track.artist }.count().toString()
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue =
                filterHistoryForTimeframe(playbackHistory, prevTimeframe).distinctBy { it.track.artist }.count()
                    .toString()
        }

        return SmallStat(
            2, "Unique Artists", "person",
            Stat(smallStatRequest.prevTimeframe, "vs. $prevValue", value)
        )
    }

    private fun getListeningTimeAndTimeSpentStat(smallStatRequest: SmallStatRequest): Array<SmallStat> {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val currentDuration = getListeningTime(currentHistory)
        var prevDuration = Duration.ZERO
        if (!prevTimeframe.toDuration().isZero) {
            prevDuration = getListeningTime(prevHistory)
        }

        val currentTimeSpent = currentDuration.toMillis().toFloat() / timeframe.toDuration().toMillis().toFloat()
        var prevTimeSpent = 0f
        if (!prevTimeframe.toDuration().isZero) {
            prevTimeSpent = prevDuration.toMillis().toFloat() / prevTimeframe.toDuration().toMillis().toFloat()
        }

        val listeningTimeStat = SmallStat(
            3, "Listening Time", "schedule",
            Stat(
                prevTimeframe,
                "vs. ${prevDuration.toTimeString()}",
                currentDuration.toTimeString()
            )
        )

        val timeSpentStat = SmallStat(
            4, "Time spent listening", "data_usage",
            Stat(
                prevTimeframe,
                "vs ${(prevTimeSpent * 100).toInt()}%",
                "${(currentTimeSpent * 100).toInt()}%"
            )
        )

        return arrayOf(
            listeningTimeStat,
            timeSpentStat
        )
    }

    private fun getListeningTime(history: List<PlaybackHistoryObject>): Duration {

        val duration = history.sumOf { it.track.duration.toLong() }
        return Duration.ofMillis(duration)
    }

    private fun getMostActiveDayStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val value = getMostActiveDay(currentHistory)
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue = getMostActiveDay(prevHistory)
        }

        return SmallStat(
            5, "Most Active Day", "event",
            Stat(
                prevTimeframe,
                "vs. $prevValue",
                value
            )
        )
    }

    private fun getMostActiveDay(playbackHistory: List<PlaybackHistoryObject>): String {
        val mostActiveDaySet = playbackHistory
            .map { LocalDate.ofInstant(Date(it.playedAt).toInstant(), ZoneId.of("Europe/Berlin")) }
            .groupBy { it }
            .maxByOrNull { it.value.size }
        val formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")

        return "${formatter.format(mostActiveDaySet?.key)} (${mostActiveDaySet?.value?.size})"
    }

    private fun getAverageHappinessStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val value = getAverageHappiness(currentHistory)
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue = getAverageHappiness(prevHistory)
        }

        return SmallStat(
            6, "Average Happiness", "sentiment_very_satisfied",
            Stat(prevTimeframe, "vs. $prevValue", value)
        )

    }

    private fun getAverageHappiness(playbackHistory: List<PlaybackHistoryObject>): String {
        val avg = playbackHistory.mapNotNull { it.audioFeatures?.valence }.average()
        return "${(avg * 100).toInt()}%"
    }

    private fun getAverageEnergyStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val value = getAverageEnergy(currentHistory)
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue = getAverageEnergy(prevHistory)
        }

        return SmallStat(
            7, "Average Energy", "flash_on",
            Stat(prevTimeframe, "vs. $prevValue", value)
        )

    }

    private fun getAverageEnergy(playbackHistory: List<PlaybackHistoryObject>): String {
        val avg = playbackHistory.mapNotNull { it.audioFeatures?.energy }.average()
        return "${(avg * 100).toInt()}%"
    }

    private fun getAverageDanceabilityStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val value = getAverageDanceability(currentHistory)
        var prevValue = ""
        if (!prevTimeframe.toDuration().isZero) {
            prevValue = getAverageDanceability(prevHistory)
        }

        return SmallStat(
            8, "Average Danceability", "emoji_people",
            Stat(prevTimeframe, "vs. $prevValue", value)
        )

    }

    private fun getAverageDanceability(playbackHistory: List<PlaybackHistoryObject>): String {
        val avg = playbackHistory.mapNotNull { it.audioFeatures?.danceability }.average()
        return "${(avg * 100).toInt()}%"
    }


    private fun filterHistoryForTimeframe(
        playbackHistory: List<PlaybackHistoryObject>,
        timeframe: TimeFrame
    ): List<PlaybackHistoryObject> {
        return playbackHistory.parallelStream().filter {
            it.playedAt <= timeframe.end && it.playedAt >= timeframe.start
        }.toList()
    }
}


fun Duration.toTimeString(): String {
    var string: String
    if (toHours() > 48) {
        string = "%d days, %d hour".format(toDays(), toHoursPart())
        if (toHoursPart() > 1)
            string += "s"

    } else {
        string = "%d:%02d:%02d".format(toHours(), toMinutesPart(), toSecondsPart())
    }
    return string
}