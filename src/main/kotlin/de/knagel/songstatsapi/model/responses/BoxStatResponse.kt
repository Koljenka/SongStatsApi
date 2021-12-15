package de.knagel.songstatsapi.model.responses

import de.knagel.songstatsapi.model.BoxStat

data class BoxStatResponse(
    override val content: List<BoxStat>
) : ApiResponse

