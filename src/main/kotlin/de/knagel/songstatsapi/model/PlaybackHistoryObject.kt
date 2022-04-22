package de.knagel.songstatsapi.model

data class PlaybackHistoryObject(
        val playedAt: Int,
        val track: Track,
        val contextType: String,
        val audioFeatures: AudioFeatures,
)
