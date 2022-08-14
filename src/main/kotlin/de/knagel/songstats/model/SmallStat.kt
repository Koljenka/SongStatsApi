package de.knagel.songstats.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import de.knagel.songstats.model.Stat
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
 * @param heading 
 * @param icon 
 * @param stat 
 */
data class SmallStat(

    @Schema(example = "null", description = "")
    @field:JsonProperty("id") val id: kotlin.Int? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("heading") val heading: kotlin.String? = null,

    @Schema(example = "null", description = "")
    @field:JsonProperty("icon") val icon: kotlin.String? = null,

    @field:Valid
    @Schema(example = "null", description = "")
    @field:JsonProperty("stat") val stat: Stat? = null
) {

}

