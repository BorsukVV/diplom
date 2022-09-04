package ru.netology.neRecipes.data

import androidx.lifecycle.LiveData

interface RecipeRepository {
    val data: LiveData<List<Recipe>>
    fun chooseFavorite(recipeID: Long)
    fun delete(recipeID: Long)
    fun save(recipe: Recipe)

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_STEP_ID = 0
    }

}