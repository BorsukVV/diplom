package ru.netology.neRecipes.data

import androidx.lifecycle.LiveData

interface RecipeRepository {
    val data: LiveData<List<Recipe>>
    var stepsList: LiveData<List<Step>>
    fun chooseFavorite(recipeID: Long)
    fun delete(recipeID: Long)
    fun save(recipe: Recipe)
    fun deleteStep(stepID: Int)
    fun saveStep(step: Step)
    //fun insertStepToBuffer(step: Step)

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_STEP_ID = 0
    }

}