package de.knagel.songstats

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["de.knagel.songstats", "de.knagel.songstats.api", "de.knagel.songstats.model"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
