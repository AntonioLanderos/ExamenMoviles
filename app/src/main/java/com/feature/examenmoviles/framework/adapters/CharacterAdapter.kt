package com.feature.examenmoviles.framework.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            Glide.with(binding.characterImage.context)
                .load(character.image)
                .into(binding.characterImage)
        }
    }
}
