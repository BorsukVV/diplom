package ru.netology.neRecipes.data

import android.net.Uri
import androidx.room.PrimaryKey

data class Recipe(
    @PrimaryKey
    val id: Long,
    val title: String,
    val authorName: String,
    val categorySpinnerPosition: Int,
    val category: String,
    val description: String,
    val hasCustomImage:Boolean,
    val imageUri: Uri?,
    val isFavourite: Boolean,
)

data class Step(
    val id: Long,
    val recipeId: Long,
    val sequentialNumber: Int,
    val stepDescription: String,
    val hasCustomImage:Boolean,
    val stepImageUri: Uri?,

    )