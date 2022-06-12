package de.knagel.songstatsapi.analyser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import de.knagel.songstatsapi.model.BoxStat
import de.knagel.songstatsapi.model.TopTrackDayResponse
import de.knagel.songstatsapi.model.TopTrackMonth
import de.knagel.songstatsapi.model.requests.StatRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class PersonalSongStatsAnalyzer(private val request: StatRequest) {
    fun analyseAll(): List<BoxStat> {
        return listOfNotNull(
            analyseIsTopSongForDays(),
            analyseIsTopSongForMonths()
        )
    }

    private fun analyseIsTopSongForDays(): BoxStat? {
        val response = callPlaybackApi("isTopTrackForDays")
        val res = ObjectMapper().readValue(response, TopTrackDayResponse::class.java)
        if (res.days > 0) {
            val startDate =
                LocalDate.now().minusDays(res.days.toLong()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            return BoxStat(
                "On Repeat!",
                "${request.track.name} has been your top song for the last ${res.days} days<br \\>" +
                        "You've listened to it ${res.plays} times since $startDate",
                "repeat"
            )
        }
        return null;
    }

    private fun analyseIsTopSongForMonths(): BoxStat? {
        val response = callPlaybackApi("isTopTrackForMonths")
        val res = ObjectMapper().registerModule(JavaTimeModule()).readValue(response, Array<TopTrackMonth>::class.java).toCollection(ArrayList())
        res.reverse()
        if (res.isEmpty()) {
            return null
        }
        val (title, icon) = getTopTrackMonthTitleAndIconByMonths(res.size)
        return BoxStat(
            title,
            getTopTrackMonthDescription(res),
            icon
        )
    }

    private fun callPlaybackApi(path: String): String {
        val url = URL("https://kolkie.de/spotify-playback-api/$path");
        val body = "{\"access_token\": \"${request.accessToken}\", \"track_id\": \"${request.track.id}\"}"
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Content-Length", body.length.toString())

            DataOutputStream(outputStream).use { it.writeBytes(body) }

            BufferedReader(InputStreamReader(inputStream)).use { return it.readText() }
        }
    }

    private fun getTopTrackMonthTitleAndIconByMonths(numberOfMonths: Int): Pair<String, String> {
        return when (numberOfMonths) {
            1 -> "Top of the month" to "calendar_month"
            in 2..4 -> "One of your favorites" to "local_fire_department"
            in 5..8 -> "You can't get enough of it" to "headphones"
            else -> "Evergreen" to "favorite"
        }
    }
    private fun getTopTrackMonthDescription(topTrackMonths: ArrayList<TopTrackMonth>): String {
        return when (topTrackMonths.size) {
            1 -> {
                val date = topTrackMonths[0].firstDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
                "${request.track.name} has been your top song in $date. You played it ${topTrackMonths[0].count} times"
            }
            else -> {
                var string = "${request.track.name} has been your top song for ${topTrackMonths.size} months:<ul>"
                      topTrackMonths.forEach {
                          val date = it.firstDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
                          string += "<li>$date: ${it.count} plays</li>"
                      }
                "$string</ul>"
            }
        }
    }
}