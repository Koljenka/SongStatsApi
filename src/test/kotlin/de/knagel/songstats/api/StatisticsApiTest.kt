package de.knagel.songstats.api

import de.knagel.songstats.model.ApiError
import de.knagel.songstats.model.BoxStat
import de.knagel.songstats.model.GetSlowBoxStatsRequest
import de.knagel.songstats.model.SmallStat
import de.knagel.songstats.model.SmallStatRequest
import de.knagel.songstats.model.StatRequest
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class StatisticsApiTest {

    private val service: StatisticsApiService = StatisticsApiServiceImpl()
    private val api: StatisticsApiController = StatisticsApiController(service)

    /**
     * To test StatisticsApiController.getAverageFeatureStat
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getAverageFeatureStatTest() {
        val feature: kotlin.String = TODO()
        val smallStatRequest: SmallStatRequest = TODO()
        val response: ResponseEntity<SmallStat> = api.getAverageFeatureStat(feature, smallStatRequest)

        // TODO: test validations
    }

    /**
     * To test StatisticsApiController.getListeningTime
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getListeningTimeTest() {
        val smallStatRequest: SmallStatRequest = TODO()
        val response: ResponseEntity<SmallStat> = api.getListeningTime(smallStatRequest)

        // TODO: test validations
    }

    /**
     * To test StatisticsApiController.getNormalBoxStats
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getNormalBoxStatsTest() {
        val statRequest: StatRequest = TODO()
        val response: ResponseEntity<List<BoxStat>> = api.getNormalBoxStats(statRequest)

        // TODO: test validations
    }

    /**
     * To test StatisticsApiController.getSlowBoxStats
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getSlowBoxStatsTest() {
        val getSlowBoxStatsRequest: GetSlowBoxStatsRequest = TODO()
        val response: ResponseEntity<List<BoxStat>> = api.getSlowBoxStats(getSlowBoxStatsRequest)

        // TODO: test validations
    }

    /**
     * To test StatisticsApiController.getTimeSpent
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getTimeSpentTest() {
        val smallStatRequest: SmallStatRequest = TODO()
        val response: ResponseEntity<SmallStat> = api.getTimeSpent(smallStatRequest)

        // TODO: test validations
    }

    /**
     * To test StatisticsApiController.getUniqueArtistStat
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getUniqueArtistStatTest() {
        val smallStatRequest: SmallStatRequest = TODO()
        val response: ResponseEntity<SmallStat> = api.getUniqueArtistStat(smallStatRequest)

        // TODO: test validations
    }
}
