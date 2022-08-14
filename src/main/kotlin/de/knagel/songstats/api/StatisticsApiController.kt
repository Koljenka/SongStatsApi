package de.knagel.songstats.api

import de.knagel.songstats.model.ApiError
import de.knagel.songstats.model.BoxStat
import de.knagel.songstats.model.GetSlowBoxStatsRequest
import de.knagel.songstats.model.SmallStat
import de.knagel.songstats.model.SmallStatRequest
import de.knagel.songstats.model.StatRequest
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.*
import io.swagger.v3.oas.annotations.media.*
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.security.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

import kotlin.collections.List
import kotlin.collections.Map

@RestController
@Validated
@RequestMapping("\${api.base-path:/spotify-api}")
class StatisticsApiController(@Autowired(required = true) val service: StatisticsApiService) {

    @Operation(
        summary = "",
        operationId = "getAverageFeatureStat",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = SmallStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Small/AverageFeature"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getAverageFeatureStat(@NotNull @Parameter(description = "", required = true, schema = Schema(allowableValues = ["valence", "energy", "danceability"])) @Valid @RequestParam(value = "feature", required = true) feature: kotlin.String,@Parameter(description = "", required = true) @Valid @RequestBody smallStatRequest: SmallStatRequest): ResponseEntity<SmallStat> {
        return ResponseEntity(service.getAverageFeatureStat(feature, smallStatRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "",
        operationId = "getListeningTime",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = SmallStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Small/ListeningTime"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getListeningTime(@Parameter(description = "", required = true) @Valid @RequestBody smallStatRequest: SmallStatRequest): ResponseEntity<SmallStat> {
        return ResponseEntity(service.getListeningTime(smallStatRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "Gets BoxStats for the given song if any are found",
        operationId = "getNormalBoxStats",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = BoxStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Box/NormalStats"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getNormalBoxStats(@Parameter(description = "", required = true) @Valid @RequestBody statRequest: StatRequest): ResponseEntity<List<BoxStat>> {
        return ResponseEntity(service.getNormalBoxStats(statRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "Gets difficult to calculate BoxStats for the given song if any are found",
        operationId = "getSlowBoxStats",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = BoxStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Box/SlowStats"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getSlowBoxStats(@Parameter(description = "", required = true) @Valid @RequestBody getSlowBoxStatsRequest: GetSlowBoxStatsRequest): ResponseEntity<List<BoxStat>> {
        return ResponseEntity(service.getSlowBoxStats(getSlowBoxStatsRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "",
        operationId = "getTimeSpent",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = SmallStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Small/TimeSpent"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getTimeSpent(@Parameter(description = "", required = true) @Valid @RequestBody smallStatRequest: SmallStatRequest): ResponseEntity<SmallStat> {
        return ResponseEntity(service.getTimeSpent(smallStatRequest), HttpStatus.valueOf(200))
    }

    @Operation(
        summary = "Gets the unique artists for the given timeframe",
        operationId = "getUniqueArtistStat",
        description = "",
        responses = [
            ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = SmallStat::class))]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content(schema = Schema(implementation = ApiError::class))]),
            ApiResponse(responseCode = "200", description = "Error", content = [Content(schema = Schema(implementation = ApiError::class))]) ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/Statistics/Small/UniqueArtist"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getUniqueArtistStat(@Parameter(description = "", required = true) @Valid @RequestBody smallStatRequest: SmallStatRequest): ResponseEntity<SmallStat> {
        return ResponseEntity(service.getUniqueArtistStat(smallStatRequest), HttpStatus.valueOf(200))
    }
}
