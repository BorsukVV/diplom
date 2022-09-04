package ru.netology.neRecipes.data

data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val isFavourite: Boolean,
)

data class Step(
    val id: Int,
    val recipeId: Long,
    val stepDescription: String,
    val stepImageUrl: String,

)