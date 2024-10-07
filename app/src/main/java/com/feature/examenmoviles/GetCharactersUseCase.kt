package com.feature.examenmoviles

import com.feature.examenmoviles.data.Repository

class GetCharactersUseCase(private val repository: Repository) {

    suspend operator fun invoke(page: Int) = repository.getCharacters(page)
}