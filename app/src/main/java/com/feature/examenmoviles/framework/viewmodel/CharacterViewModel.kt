package com.feature.examenmoviles.framework.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.feature.examenmoviles.GetCharactersUseCase
import com.feature.examenmoviles.data.CharacterRepository
import com.feature.examenmoviles.data.Repository
import com.feature.examenmoviles.data.network.DragonBallAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.feature.examenmoviles.framework.model.CharacterResponse
import com.feature.examenmoviles.framework.model.DBCharacter

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<DBCharacter>?>()
    val characters: MutableLiveData<List<DBCharacter>?> get() = _characters

    // Cargar todas las páginas de personajes
    fun fetchAllCharacters() {
        val allCharacters = mutableListOf<DBCharacter>()
        fetchPage(1, allCharacters)
    }

    private fun fetchPage(page: Int, allCharacters: MutableList<DBCharacter>) {
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(page)
                val characters = response.items
                allCharacters.addAll(characters)

                // Si no estamos en la última página, sigue cargando la siguiente página
                if (response.meta.currentPage < response.meta.totalPages) {
                    fetchPage(page + 1, allCharacters)  // Llama recursivamente para la siguiente página
                } else {
                    // Cuando se hayan cargado todas las páginas, actualiza el LiveData
                    _characters.postValue(allCharacters)
                }
            } catch (e: Exception) {
                // Manejar el error en caso de que ocurra
            }
        }
    }

    // Filtrar personajes por raza y afiliación
    fun applyFilters(filters: Map<String, String>) {
        viewModelScope.launch {
            val filteredCharacters = _characters.value?.filter { character ->
                val matchesRace = filters["race"]?.let { character.race.contains(it, ignoreCase = true) } ?: true
                val matchesAffiliation = filters["affiliation"]?.let { character.affiliation.contains(it, ignoreCase = true) } ?: true
                matchesRace && matchesAffiliation
            }
            _characters.postValue(filteredCharacters)
        }
    }
}