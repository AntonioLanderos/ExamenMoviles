package com.feature.examenmoviles.framework.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.feature.examenmoviles.data.CharacterRepository
import com.feature.examenmoviles.databinding.ActivityCharacterDetailBinding
import kotlinx.coroutines.*
import com.feature.examenmoviles.data.network.APIClient
import com.feature.examenmoviles.framework.model.DBCharacter

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val repository = CharacterRepository(APIClient.api)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val characterId = intent.getIntExtra("character_id", -1)
        loadCharacterDetails(characterId)
    }

    private fun loadCharacterDetails(characterId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val character = withContext(Dispatchers.IO) {
                    repository.getCharacterById(characterId)
                }
                showCharacterDetails(character)
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }

    private fun showCharacterDetails(character: DBCharacter) {
        binding.characterName.text = character.name
        binding.characterAffiliation.text = character.affiliation
        binding.characterKi.text = "Ki: ${character.ki}"
        binding.characterOriginPlanet.text = "Origin: ${character.originPlanet.name}"

        Glide.with(this)
            .load(character.image)
            .into(binding.characterImage)

        // Puedes mostrar más detalles del planeta si lo deseas
        binding.characterOriginDescription.text = character.originPlanet.description
        if (character.originPlanet.isDestroyed) {
            binding.planetStatus.text = "This planet is destroyed."
        } else {
            binding.planetStatus.text = "This planet is still intact."
        }
    }
}