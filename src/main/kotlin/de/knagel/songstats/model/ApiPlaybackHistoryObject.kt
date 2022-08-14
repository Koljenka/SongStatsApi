package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import de.knagel.songstats.model.ApiAudioFeatures
import de.knagel.songstats.model.ApiTrack
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
 * @param playedAt 
 * @param track 
 * @param contextType 
 * @param audioFeatures 
 */
data class ApiPlaybackHistoryObject(

    @Schema(example = "null", description = "")
    @field:JsonProperty("playedAt") val playedAt: kotlin.Long? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("track") val track: ApiTrack? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("contextType") val contextType: kotlin.String? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("audioFeatures") val audioFeatures: ApiAudioFeatures? = null
) {

}

