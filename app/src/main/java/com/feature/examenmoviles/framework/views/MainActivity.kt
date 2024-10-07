package com.feature.examenmoviles.framework.views

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.feature.examenmoviles.R
import com.feature.examenmoviles.data.CharacterRepository
import com.feature.examenmoviles.data.network.APIClient
import com.feature.examenmoviles.databinding.ActivityMainBinding
import com.feature.examenmoviles.framework.viewmodel.CharacterViewModel
import com.feature.examenmoviles.framework.viewmodel.CharacterViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = CharacterRepository(APIClient.api)
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(repository)
    }

    private var selectedRace: String? = null
    private var selectedAffiliation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        val adapter = CharacterAdapter(listOf())
        binding.characterList.layoutManager = LinearLayoutManager(this)
        binding.characterList.adapter = adapter

        // Configurar los filtros
        setupFilters()

        // Observa los cambios en los personajes filtrados
        viewModel.filteredCharacters.observe(this) { characters ->
            if (characters != null) {
                adapter.updateData(characters)
            }

            // Mantener el filtro seleccionado en los Spinners
            selectedRace?.let {
                val racePosition = (binding.spinnerRace.adapter as ArrayAdapter<String>).getPosition(it)
                binding.spinnerRace.setSelection(racePosition)
            }

            selectedAffiliation?.let {
                val affiliationPosition = (binding.spinnerAffiliation.adapter as ArrayAdapter<String>).getPosition(it)
                binding.spinnerAffiliation.setSelection(affiliationPosition)
            }
        }

        // Cargar todos los personajes (sin límite de página)
        viewModel.fetchAllCharacters()

        // Acción del botón para aplicar los filtros
        binding.buttonApplyFilter.setOnClickListener {
            selectedRace = binding.spinnerRace.selectedItem.toString()
            selectedAffiliation = binding.spinnerAffiliation.selectedItem.toString()

            // Aplicar filtros en función de la raza y afiliación seleccionadas
            applyFilters(selectedRace, selectedAffiliation)
        }
    }

    // Método para configurar los filtros con Spinner
    private fun setupFilters() {
        val raceOptions = listOf("All", "Saiyan", "Human", "Namekian", "Frieza Race", "Android", "Other")
        val affiliationOptions = listOf("All", "Z Fighter", "Army of Frieza", "Freelancer", "Other")

        // Configurar el adaptador del Spinner de razas
        val raceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, raceOptions)
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRace.adapter = raceAdapter

        // Configurar el adaptador del Spinner de afiliaciones
        val affiliationAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, affiliationOptions)
        affiliationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAffiliation.adapter = affiliationAdapter
    }

    // Método para aplicar los filtros de raza y afiliación
    private fun applyFilters(selectedRace: String?, selectedAffiliation: String?) {
        val raceFilter = if (selectedRace == "All") null else selectedRace
        val affiliationFilter = if (selectedAffiliation == "All") null else selectedAffiliation

        // Filtrar los personajes utilizando los filtros seleccionados
        viewModel.applyFilters(raceFilter, affiliationFilter)
    }
}
