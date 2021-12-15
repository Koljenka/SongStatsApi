package de.knagel.songstatsapi.model.responses

interface ApiResponse {
    val message: String
        get() = this.javaClass.simpleName
    val content: Any?

    companion object {
        fun create(content: Any): ApiResponse {
            return object : ApiResponse {
                override val content: Any = content
            }
        }
    }
}