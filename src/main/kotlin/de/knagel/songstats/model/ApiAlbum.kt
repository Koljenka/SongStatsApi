package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
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
 * @param id 
 * @param albumType 
 * @param name 
 * @param releaseDate 
 * @param totalTracks 
 * @param tracks 
 */
data class ApiAlbum(

    @Schema(example = "null", description = "")
    @field:JsonProperty("id") val id: kotlin.String? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("albumType") val albumType: ApiAlbum.AlbumType? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("releaseDate") val releaseDate: java.time.LocalDate? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("totalTracks") val totalTracks: kotlin.Int? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("tracks") val tracks: kotlin.collections.List<ApiTrack>? = null
) {

    /**
    * 
    * Values: album,compilation,single
    */
    enum class AlbumType(val value: kotlin.String) {

        @JsonProperty("album") album("album"),
        @JsonProperty("compilation") compilation("compilation"),
        @JsonProperty("single") single("single")
    }

}

