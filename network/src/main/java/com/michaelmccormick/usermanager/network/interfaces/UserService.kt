package com.michaelmccormick.usermanager.network.interfaces

import com.michaelmccormick.usermanager.network.BuildConfig
import com.michaelmccormick.usermanager.network.models.UserNetworkEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET(USERS_ENDPOINT)
    suspend fun getUsers(): List<UserNetworkEntity>

    @POST(USERS_ENDPOINT)
    suspend fun createUser(
        @Body userEntity: UserNetworkEntity,
        @Header(AUTH_HEADER_KEY) headerKey: String = buildAuthHeader(),
    ): UserNetworkEntity

    @DELETE("$USERS_ENDPOINT/{$ID_PATH}")
    suspend fun deleteUser(
        @Path(ID_PATH) userId: Int,
        @Header(AUTH_HEADER_KEY) headerKey: String = buildAuthHeader(),
    ): Response<Unit>

    private companion object {
        const val USERS_ENDPOINT = "users"
        const val ID_PATH = "id"
        const val AUTH_HEADER_KEY = "Authorization"
        fun buildAuthHeader() = "Bearer ${BuildConfig.GO_REST_KEY}"
    }
}
