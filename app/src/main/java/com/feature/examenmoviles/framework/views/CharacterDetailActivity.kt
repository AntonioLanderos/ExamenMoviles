package com.feature.examenmoviles.framework.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.feature.examenmoviles.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir los datos del Intent
        val characterName = intent.getStringExtra("character_name")
        val characterAffiliation = intent.getStringExtra("character_affiliation")
        val characterKi = intent.getStringExtra("character_ki")
        val characterMaxKi = intent.getStringExtra("character_max_ki")
        val characterRace = intent.getStringExtra("character_race")
        val characterGender = intent.getStringExtra("character_gender")
        val characterDescription = intent.getStringExtra("character_description")
        val originPlanet = intent.getStringExtra("character_origin_planet")
        val characterImage = intent.getStringExtra("character_image")  // Recibe la URL de la imagen

        // Mostrar los datos en la vista
        binding.characterName.text = characterName
        binding.characterAffiliation.text = characterAffiliation
        binding.characterKi.text = "Ki: $characterKi"
        binding.characterMaxKi.text = "Max Ki: $characterMaxKi"
        binding.characterRace.text = "Race: $characterRace"
        binding.characterGender.text = "Gender: $characterGender"
        binding.characterDescription.text = characterDescription
        binding.characterOriginPlanet.text = originPlanet

        // Cargar la imagen usando Glide
        Glide.with(this)
            .load(characterImage)
            .into(binding.characterImage)
    }
}
