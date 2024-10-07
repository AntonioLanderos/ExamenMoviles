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

    val characters = MutableLiveData<List<DBCharacter>>()

    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(page)
                Log.d("CharacterViewModel", "Characters fetched: ${response.items.size}")
                characters.value = response.items
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error fetching characters", e)
            }
        }
    }

    fun applyFilters(filters: Map<String, String>) {
        viewModelScope.launch {
            characters.value = repository.getFilteredCharacters(filters)
        }
    }
}