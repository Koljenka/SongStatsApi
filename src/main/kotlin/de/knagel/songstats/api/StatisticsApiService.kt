package de.knagel.songstats.api

import de.knagel.songstats.model.ApiError
import de.knagel.songstats.model.BoxStat
import de.knagel.songstats.model.GetSlowBoxStatsRequest
import de.knagel.songstats.model.SmallStat
import de.knagel.songstats.model.SmallStatRequest
import de.knagel.songstats.model.StatRequest

interface StatisticsApiService {

    /**
     * POST /Statistics/Small/AverageFeature
     *
     * @param feature  (required)
     * @param smallStatRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getAverageFeatureStat
     */
    fun getAverageFeatureStat(feature: kotlin.String, smallStatRequest: SmallStatRequest): SmallStat

    /**
     * POST /Statistics/Small/ListeningTime
     *
     * @param smallStatRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getListeningTime
     */
    fun getListeningTime(smallStatRequest: SmallStatRequest): SmallStat

    /**
     * POST /Statistics/Box/NormalStats : Gets BoxStats for the given song if any are found
     *
     * @param statRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getNormalBoxStats
     */
    fun getNormalBoxStats(statRequest: StatRequest): List<BoxStat>

    /**
     * POST /Statistics/Box/SlowStats : Gets difficult to calculate BoxStats for the given song if any are found
     *
     * @param getSlowBoxStatsRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getSlowBoxStats
     */
    fun getSlowBoxStats(getSlowBoxStatsRequest: GetSlowBoxStatsRequest): List<BoxStat>

    /**
     * POST /Statistics/Small/TimeSpent
     *
     * @param smallStatRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getTimeSpent
     */
    fun getTimeSpent(smallStatRequest: SmallStatRequest): SmallStat

    /**
     * POST /Statistics/Small/UniqueArtist : Gets the unique artists for the given timeframe
     *
     * @param smallStatRequest  (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Error (status code 200)
     * @see StatisticsApi#getUniqueArtistStat
     */
    fun getUniqueArtistStat(smallStatRequest: SmallStatRequest): SmallStat
}
