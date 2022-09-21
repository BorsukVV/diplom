package ru.netology.neRecipes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface RecipeRepository {
    val data: LiveData<List<Recipe>>
    val favorites: LiveData<List<Recipe>>
    var stepsList: MutableLiveData<List<Step>>
    fun recipeSteps(recipeID: Long): List<Step>
    fun chooseFavorite(recipeID: Long)
    fun delete(recipeID: Long)
    fun save(recipe: Recipe): Long
    fun deleteStep(stepID: Int)
    fun saveStep(step: Step)
    fun associateStepsWithRecipe(newRecipeId: Long)
    fun addStepToList(step: Step)

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_STEP_ID = 0
    }

}