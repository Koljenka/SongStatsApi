package de.knagel.songstatsapi.model.requests

import de.knagel.songstatsapi.model.Album
import de.knagel.songstatsapi.model.Track

data class AlbumStatsRequest(
    val album: Album,
    val track: Track
)
