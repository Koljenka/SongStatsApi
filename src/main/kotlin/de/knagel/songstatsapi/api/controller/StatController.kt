package de.knagel.songstatsapi.api.controller

import de.knagel.songstatsapi.analyser.AlbumAnalyser
import de.knagel.songstatsapi.analyser.AudioFeaturesAnalyser
import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.requests.StatRequest
import de.knagel.songstatsapi.model.responses.ApiResponse
import de.knagel.songstatsapi.model.responses.BoxStatResponse
import de.knagel.songstatsapi.model.responses.NoStatsFoundResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StatController {

    @CrossOrigin(origins = ["http://localhost"])
    @PostMapping("/stats")
    fun stats(@RequestBody statsRequest: StatRequest): ApiResponse {
        val statList = emptyList<BoxStat>()
            .union(AlbumAnalyser.analyseAll(statsRequest))
            .union(AudioFeaturesAnalyser(statsRequest).analyseAll())
            .toList()

        return if (statList.isNotEmpty()) BoxStatResponse(statList) else NoStatsFoundResponse
    }
}