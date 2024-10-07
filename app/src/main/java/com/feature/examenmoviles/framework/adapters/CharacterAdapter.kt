package com.feature.examenmoviles.framework.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feature.examenmoviles.R
import com.feature.examenmoviles.databinding.ItemCharacterBinding
import com.feature.examenmoviles.framework.model.DBCharacter

class CharacterAdapter(private var characters: List<DBCharacter>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount() = characters.size

    fun updateData(newCharacters: List<DBCharacter>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        private val nameTextView: TextView = itemView.findViewById(R.id.character_name)
        private val affiliationTextView: TextView = itemView.findViewById(R.id.character_affiliation)
        private val kiTextView: TextView = itemView.findViewById(R.id.character_ki)

        fun bind(character: DBCharacter) {
            binding.characterName.text = character.name
            binding.characterAffiliation.text = character.affiliation
            binding.characterKi.text = "Ki: ${character.ki}"

            val planetName = character.originPlanet?.name ?: "Unknown Planet"
            binding.characterOriginPlanet.text = planetName

            Glide.with(binding.characterImage.context)
                .load(character.image)
                .into(binding.characterImage)

            // Click listener para mostrar más detalles
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, CharacterDetailActivity::class.java).apply {
                    putExtra("character_name", character.name)
                    putExtra("character_affiliation", character.affiliation)
                    putExtra("character_ki", character.ki)
                    putExtra("character_max_ki", character.maxKi)
                    putExtra("character_race", character.race)
                    putExtra("character_gender", character.gender)
                    putExtra("character_description", character.description)
                    putExtra("character_origin_planet", planetName)
                    putExtra("character_image", character.image)
                }
                binding.root.context.startActivity(intent)
            }
        }

    }
}