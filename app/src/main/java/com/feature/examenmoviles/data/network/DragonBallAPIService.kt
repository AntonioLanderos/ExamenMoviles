package com.feature.examenmoviles.data.network

import com.feature.examenmoviles.framework.model.CharacterResponse
import com.feature.examenmoviles.framework.model.DBCharacter
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonBallAPIService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): CharacterResponse

    @GET("characters")
    suspend fun filterCharacters(
        @Query("race") race: String? = null,
        @Query("gender") gender: String? = null,
        @Query("affiliation") affiliation: String? = null
    ): List<DBCharacter>
}

