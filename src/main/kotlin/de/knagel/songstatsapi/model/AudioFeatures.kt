package de.knagel.songstatsapi.model

import se.michaelthelin.spotify.enums.Modality

data class AudioFeatures(
        val id: String,
        val acousticness: Float,
        val danceability: Float,
        val energy: Float,
        val instrumentalness: Float,
        val key: Int,
        val liveness: Float,
        val loudness: Float,
        val mode: Modality,
        val speechiness: Float,
        val tempo: Float,
        val timeSignature: Int,
        val valence: Float
)
