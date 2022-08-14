package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import de.knagel.songstats.model.ApiAlbum
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
 * @param accessToken 
 * @param track 
 * @param album 
 */
data class StatRequest(

    @Schema(example = "null", description = "")
    @field:JsonProperty("accessToken") val accessToken: kotlin.String? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("track") val track: ApiTrack? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("album") val album: ApiAlbum? = null
) {

}

