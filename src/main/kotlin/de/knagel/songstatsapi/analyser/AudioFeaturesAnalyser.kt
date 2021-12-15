package de.knagel.songstatsapi.analyser

import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.requests.StatRequest
import se.michaelthelin.spotify.SpotifyApi
import java.util.*
import kotlin.math.roundToInt

class AudioFeaturesAnalyser(private val request: StatRequest) {
    private val api: SpotifyApi = SpotifyApi.builder().setAccessToken(request.accessToken).build()
    private val audioFeatures =
        api.getAudioFeaturesForSeveralTracks(*request.album.tracks.map { t -> t.id }.toTypedArray()).build().execute()
            .toList()

    fun analyseAll(): List<BoxStat> {
        return listOfNotNull(
            analyseIsFastest(request),
            analyseIsHighestHappiness(request)
        )
    }

    private val analyseIsHighestHappiness = StatAnalyser { (_, track, album) ->
        val max = audioFeatures.maxByOrNull { aF -> aF.valence }
        if (max?.id == track.id) {
            return@StatAnalyser BoxStat(
                "${String.format(Locale.ENGLISH,"%.2f", max.valence * 100)}% Happy!",
                "No other Song in '${album.name}' is as happy as '${track.name}'",
                "mood"
            )
        }
        return@StatAnalyser null
    }

    private val analyseIsFastest = StatAnalyser { (_, track, album) ->
        val max = audioFeatures.maxByOrNull { aF -> aF.tempo }
        if (max?.id == track.id) {
            return@StatAnalyser BoxStat(
                "Speed of Light",
                "With ${max.tempo.roundToInt()}bpm is '${track.name}' the fastest Song in '${album.name}'",
                "speed"
            )
        }
        return@StatAnalyser null
    }

}