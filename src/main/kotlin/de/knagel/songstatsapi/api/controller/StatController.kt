package de.knagel.songstatsapi.api.controller

import de.knagel.songstatsapi.analyser.AlbumAnalyser
import de.knagel.songstatsapi.analyser.AudioFeaturesAnalyser
import de.knagel.songstatsapi.analyser.SmallStatAnalyser
import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.SmallStat
import de.knagel.songstatsapi.model.requests.SmallStatRequest
import de.knagel.songstatsapi.model.requests.StatRequest
import de.knagel.songstatsapi.model.responses.ApiResponse
import de.knagel.songstatsapi.model.responses.BoxStatResponse
import de.knagel.songstatsapi.model.responses.NoStatsFoundResponse
import de.knagel.songstatsapi.model.responses.SmallStatResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StatController {

    @CrossOrigin(origins = ["*"])
    @PostMapping("/stats")
    fun stats(@RequestBody statsRequest: StatRequest): ApiResponse {
        val statList = emptyList<BoxStat>()
            .union(AlbumAnalyser.analyseAll(statsRequest))
            .union(AudioFeaturesAnalyser(statsRequest).analyseAll())
            .toList()

        return if (statList.isNotEmpty()) BoxStatResponse(statList) else NoStatsFoundResponse
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping("/smallStats")
    fun smallStats(@RequestBody smallStatRequest: SmallStatRequest): ApiResponse {
        val statList = emptyList<SmallStat>()
                .union(SmallStatAnalyser.analyseAll(smallStatRequest))
                .toList()

        return if (statList.isNotEmpty()) SmallStatResponse(statList) else NoStatsFoundResponse
    }

}