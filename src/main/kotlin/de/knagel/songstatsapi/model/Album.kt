package de.knagel.songstatsapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class Album(
    val albumType: String,
    val id: String,
    val label: String,
    val name: String,
    val popularity: Int,
    val releaseDate: Date,
    val totalTracks: Int,
    val tracks: List<Track>
)
