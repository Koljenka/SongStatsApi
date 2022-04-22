package de.knagel.songstatsapi.model.responses

import de.knagel.songstatsapi.model.SmallStat

data class SmallStatResponse(
        override val content: List<SmallStat>
): ApiResponse
