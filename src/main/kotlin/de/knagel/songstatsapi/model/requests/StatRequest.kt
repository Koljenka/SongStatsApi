package de.knagel.songstatsapi.model.requests

import de.knagel.songstatsapi.model.Album
import de.knagel.songstatsapi.model.Track

data class StatRequest(
    val accessToken: String,
    val track: Track,
    val album: Album
)
