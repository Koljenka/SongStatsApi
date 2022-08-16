package de.knagel.songstats.api

import de.knagel.songstats.analyzer.AlbumAnalyzer
import de.knagel.songstats.analyzer.PersonalSongStatAnalyzer
import de.knagel.songstats.model.*
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.stream.Collectors

@Service
class StatisticsApiServiceImpl : StatisticsApiService {

    private val averageableAudioFeatures = mapOf(
        "valence" to AverageableAudioFeature(6, "Average Happiness", "sentiment_very_satisfied", "valence"),
        "energy" to AverageableAudioFeature(7, "Average Energy", "flash_on", "energy"),
        "danceability" to AverageableAudioFeature(8, "Average Danceability", "emoji_people", "danceability")
    )

    override fun getAverageFeatureStat(feature: kotlin.String, smallStatRequest: SmallStatRequest): SmallStat {
        averageableAudioFeatures[feature]?.let { avgFeature ->
            val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
            val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
            val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

            val value = getAverageFeature(feature, currentHistory)
            val prevValue = if (prevHistory.isEmpty()) "" else getAverageFeature(avgFeature.featureName, prevHistory)
            return SmallStat(
                avgFeature.id,
                avgFeature.heading,
                avgFeature.icon,
                Stat(prevTimeframe, "vs. $prevValue", value)
            )
        }
        throw BadRequestException("Feature $feature does not exist")
    }

    override fun getListeningTime(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val currentDuration = getListeningTime(currentHistory)
        var prevDuration = Duration.ZERO
        if (prevTimeframe?.toDuration()?.isZero == false) {
            prevDuration = getListeningTime(prevHistory)
        }

        return SmallStat(
            3, "Listening Time", "schedule",
            Stat(
                prevTimeframe,
                "vs. ${prevDuration.toTimeString()}",
                currentDuration.toTimeString()
            )
        )
    }

    override fun getNormalBoxStats(statRequest: StatRequest): List<BoxStat> = AlbumAnalyzer.analyseAll(statRequest)

    override fun getSlowBoxStats(request: GetSlowBoxStatsRequest): List<BoxStat> =
        PersonalSongStatAnalyzer.analyseAll(request)

    override fun getTimeSpent(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val currentHistory = filterHistoryForTimeframe(playbackHistory, timeframe)
        val prevHistory = filterHistoryForTimeframe(playbackHistory, prevTimeframe)

        val currentDuration = getListeningTime(currentHistory)
        var prevDuration = Duration.ZERO
        if (prevTimeframe?.toDuration()?.isZero == false) {
            prevDuration = getListeningTime(prevHistory)
        }

        val currentTimeSpent =
            currentDuration.toMillis().toFloat() / (timeframe?.toDuration()?.toMillis()?.toFloat() ?: 1f)
        var prevTimeSpent = 0f
        if (prevTimeframe?.toDuration()?.isZero == false) {
            prevTimeSpent = prevDuration.toMillis().toFloat() / prevTimeframe.toDuration().toMillis().toFloat()
        }

        return SmallStat(
            4, "Time spent listening", "data_usage",
            Stat(
                prevTimeframe,
                "vs ${(prevTimeSpent * 100).toInt()}%",
                "${(currentTimeSpent * 100).toInt()}%"
            )
        )
    }

    override fun getUniqueArtistStat(smallStatRequest: SmallStatRequest): SmallStat {
        val (_, playbackHistory, timeframe, prevTimeframe) = smallStatRequest
        val value =
            filterHistoryForTimeframe(playbackHistory, timeframe).distinctBy { it.track?.artist }.count().toString()
        var prevValue = ""
        if (prevTimeframe?.toDuration()?.isZero == false) {
            prevValue =
                filterHistoryForTimeframe(playbackHistory, prevTimeframe).distinctBy { it.track?.artist }.count()
                    .toString()
        }

        return SmallStat(
            2, "Unique Artists", "person",
            Stat(smallStatRequest.prevTimeframe, "vs. $prevValue", value)
        )
    }

    private fun getListeningTime(history: List<ApiPlaybackHistoryObject>): Duration {
        val duration = history.sumOf { it.track?.duration?.toLong() ?: 0 }
        return Duration.ofMillis(duration)
    }

    private fun getAverageFeature(featureName: String, playbackHistory: List<ApiPlaybackHistoryObject>): String {
        val avg = playbackHistory.mapNotNull { it.audioFeatures?.getByName(featureName) }.average()
        return "${(avg * 100).toInt()}%"
    }

    private fun filterHistoryForTimeframe(
        playbackHistory: List<ApiPlaybackHistoryObject>?,
        timeframe: Timeframe?
    ): List<ApiPlaybackHistoryObject> {
        return playbackHistory?.parallelStream()?.filter {
            (it.playedAt ?: 0) <= (timeframe?.end ?: 0) && (it.playedAt ?: 0) >= (timeframe?.start ?: 0)
        }?.collect(Collectors.toList()) ?: emptyList()
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

    fun Timeframe.toDuration(): Duration = Duration.ofMillis(end?.minus(start ?: 0) ?: 0)

    fun ApiAudioFeatures.getByName(name: String): Float? = when (name) {
        "danceability" -> danceability
        "energy" -> energy
        "valence" -> valence
        else -> null
    }

    internal data class AverageableAudioFeature(
        val id: Int, val heading: String, val icon: String, val featureName: String
    )
}
