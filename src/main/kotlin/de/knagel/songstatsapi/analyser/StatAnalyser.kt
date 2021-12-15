package de.knagel.songstatsapi.analyser

import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.requests.StatRequest
import de.knagel.songstatsapi.model.responses.ApiResponse

fun interface StatAnalyser {
    operator fun invoke(statRequest: StatRequest): BoxStat?
}