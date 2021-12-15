package de.knagel.songstatsapi.model

import de.knagel.songstatsapi.model.responses.BoxStatResponse

data class BoxStat(
    val title: String,
    val description: String,
    val icon: String
)
