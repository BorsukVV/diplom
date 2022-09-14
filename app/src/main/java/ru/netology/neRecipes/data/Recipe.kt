package ru.netology.neRecipes.data

import android.net.Uri

data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val category: String,
    val description: String,
    val imageUri: Uri?,
    val isFavourite: Boolean,
)

data class Step(
    val id: Int,
    val recipeId: Long,
    val stepDescription: String,
    val stepImageUri: Uri?,

    )