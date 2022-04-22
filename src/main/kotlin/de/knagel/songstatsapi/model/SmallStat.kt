package de.knagel.songstatsapi.model

data class SmallStat(
        val id: Int,
        val heading: String,
        val icon: String,
        val stat: Stat

)

data class Stat (
        val prevTimeframe: TimeFrame,
        val prevValue: String,
        val value: String
)
