package com.feature.examenmoviles.data

import com.feature.examenmoviles.data.network.DragonBallAPIService
import com.feature.examenmoviles.framework.model.CharacterResponse
import com.feature.examenmoviles.framework.model.DBCharacter

class CharacterRepository(private val api: DragonBallAPIService) {

    suspend fun getCharacters(page: Int): CharacterResponse {
        return api.getCharacters(page)
    }

    suspend fun getFilteredCharacters(filters: Map<String, String>): List<DBCharacter> {
        val race = filters["race"]
        val gender = filters["gender"]
        val affiliation = filters["affiliation"]
        return api.filterCharacters(race, gender, affiliation)
    }

    suspend fun getCharacterById(id: Int) = api.getCharacterById(id)
}