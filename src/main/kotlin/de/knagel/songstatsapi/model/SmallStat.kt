package de.knagel.songstatsapi.model

data class SmallStat(
        val id: Int,
        val heading: String,
        val icon: String,
        val prevTimeframe: TimeFrame,
        val prevValue: String,
        val value: String
)
