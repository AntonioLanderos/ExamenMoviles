package com.feature.examenmoviles.framework.views

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feature.examenmoviles.GetCharactersUseCase
import com.feature.examenmoviles.R
import com.feature.examenmoviles.data.CharacterRepository
import com.feature.examenmoviles.data.network.APIClient
import com.feature.examenmoviles.data.network.DragonBallAPIService
import com.feature.examenmoviles.data.network.NetworkModuleDI
import com.feature.examenmoviles.databinding.ActivityMainBinding
import com.feature.examenmoviles.framework.viewmodel.CharacterViewModel
import com.feature.examenmoviles.framework.viewmodel.CharacterViewModelFactory

class MainActivity : AppCompatActivity() {

    private val repository = CharacterRepository(APIClient.api)
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CharacterAdapter(listOf())
        val recyclerView = findViewById<RecyclerView>(R.id.character_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.characters.observe(this, { characters ->
            Log.d("MainActivity", "Number of characters: ${characters.size}")
            adapter.updateData(characters)  // Asegúrate de tener este método en tu adaptador
        })

        viewModel.fetchCharacters(1)
    }
}