package com.feature.examenmoviles.framework.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
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

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: DBCharacter) {
            binding.characterName.text = character.name
            binding.characterAffiliation.text = character.affiliation
            binding.characterKi.text = "Ki: ${character.ki}"

            val planetName = character.originPlanet?.name ?: "Unknown Planet"  // Si es null, muestra 'Unknown Planet'
            binding.characterOriginPlanet.text = planetName

            Glide.with(binding.characterImage.context)
                .load(character.image)  // Asumiendo que tienes un campo imageUrl en DBCharacter
                .into(binding.characterImage)


            // Click listener para mostrar más detalles
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, CharacterDetailActivity::class.java)
                intent.putExtra("character_id", character.id)
                binding.root.context.startActivity(intent)
            }
        }
    }
}