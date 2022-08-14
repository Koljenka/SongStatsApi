package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 
 * @param danceability 
 * @param energy 
 * @param valence 
 * @param key 
 * @param mode 1 is major 0 is minor
 * @param tempo 
 */
data class ApiAudioFeatures(

    @Schema(example = "null", description = "")
    @field:JsonProperty("danceability") val danceability: kotlin.Float? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("energy") val energy: kotlin.Float? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("valence") val valence: kotlin.Float? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("key") val key: kotlin.Int? = null,

    @get:Min(0)
    @get:Max(1)
    @Schema(example = "null", description = "1 is major 0 is minor")
    @field:JsonProperty("mode") val mode: kotlin.Int? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("tempo") val tempo: kotlin.Float? = null
) {

}

