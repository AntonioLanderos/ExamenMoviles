package com.feature.examenmoviles.framework.model

data class CharacterResponse(
    val items: List<DBCharacter>,
    val meta: Meta
)

data class Meta(
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int
)