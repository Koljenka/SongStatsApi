package de.knagel.songstatsapi.model.responses

object NoStatsFoundResponse: ApiResponse {
    override val message = "No Stats were found for the given track"
    override val content: Any?
        get() = null
}