package com.michaelmccormick.usermanager.repositories.models

import com.google.gson.Gson
import retrofit2.HttpException

internal data class GoRestError(
    val field: String? = null,
    val message: String? = null,
)

internal fun HttpException.parseGoRestErrorBody(gson: Gson): Array<GoRestError>? =
    try {
        gson.fromJson(response()?.errorBody()?.charStream(), Array<GoRestError>::class.java)
    } catch (_: Exception) {
        null
    }
