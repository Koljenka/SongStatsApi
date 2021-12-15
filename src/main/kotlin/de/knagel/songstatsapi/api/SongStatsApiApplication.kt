package de.knagel.songstatsapi.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SongStatsApiApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            runApplication<SongStatsApiApplication>(*args)
        }
    }
}



