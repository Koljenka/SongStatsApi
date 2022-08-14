package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import de.knagel.songstats.model.Timeframe
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
 * @param prevTimeframe 
 * @param prevValue 
 * @param &#x60;value&#x60; 
 */
data class Stat(

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("prevTimeframe") val prevTimeframe: Timeframe? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("prevValue") val prevValue: kotlin.String? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("value") val `value`: kotlin.String? = null
) {

}

