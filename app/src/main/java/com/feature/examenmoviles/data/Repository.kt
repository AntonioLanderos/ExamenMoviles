package com.feature.examenmoviles.data

import com.feature.examenmoviles.data.network.APIClient
import com.feature.examenmoviles.data.network.DragonBallAPIService
import com.feature.examenmoviles.framework.model.CharacterResponse
import com.feature.examenmoviles.framework.model.DBCharacter

interface Repository {
    suspend fun getCharacters(page: Int): List<DBCharacter>
    suspend fun getFilteredCharacters(filters: Map<String, String>): List<DBCharacter>
}