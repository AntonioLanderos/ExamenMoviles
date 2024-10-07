package com.feature.examenmoviles.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.examenmoviles.data.CharacterRepository
import com.feature.examenmoviles.framework.model.DBCharacter
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    // Lista original completa de personajes
    private val _originalCharacters = mutableListOf<DBCharacter>()

    // Lista filtrada que observa la UI
    private val _filteredCharacters = MutableLiveData<List<DBCharacter>?>()
    val filteredCharacters: LiveData<List<DBCharacter>?> get() = _filteredCharacters

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
                    // Cuando se hayan cargado todas las páginas, guarda la lista original
                    _originalCharacters.addAll(allCharacters)
                    // Actualiza el LiveData con la lista completa
                    _filteredCharacters.postValue(_originalCharacters)
                }
            } catch (e: Exception) {
                // Manejar el error en caso de que ocurra
            }
        }
    }

    // Aplicar filtro por raza y afiliación
    fun applyFilters(selectedRace: String?, selectedAffiliation: String?) {
        viewModelScope.launch {
            val filteredList = _originalCharacters.filter { character ->
                val matchesRace = selectedRace?.let { character.race.contains(it, ignoreCase = true) } ?: true
                val matchesAffiliation = selectedAffiliation?.let { character.affiliation.contains(it, ignoreCase = true) } ?: true
                matchesRace && matchesAffiliation
            }
            _filteredCharacters.postValue(filteredList)
        }
    }
}
