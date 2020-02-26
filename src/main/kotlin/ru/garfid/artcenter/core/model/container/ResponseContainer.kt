package ru.garfid.artcenter.core.model.container

import com.fasterxml.jackson.annotation.JsonInclude

data class ResponseContainer(
        val ok:Boolean = false,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val error: String?,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        val result: Any?
)