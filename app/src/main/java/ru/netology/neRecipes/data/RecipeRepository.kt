package ru.netology.neRecipes.data

import androidx.lifecycle.LiveData

interface RecipeRepository {
//    val data: LiveData<List<Recipe>>
    //val filtersSetData: MutableLiveData<List<CheckBoxSettings>>
fun getFilteredRecipes(categoryIndexes: List<Int>): LiveData<List<Recipe>>
    val favorites: LiveData<List<Recipe>>
    fun recipeSteps(recipeID: Long): LiveData<List<Step>>
    fun chooseFavorite(recipeID: Long)
    fun delete(recipeID: Long)
    fun save(recipe: Recipe): Long
    fun deleteStep(stepID: Long)
    fun saveStep(step: Step)
    fun associateStepsWithRecipe(newRecipeId: Long)
    fun getRecipeByID(id: Long): Recipe
    fun searchDatabase(searchQuery: String): LiveData<List<Recipe>>

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_STEP_ID = 0L
    }
//
//    fun checkBoxSelected(checkBox: CheckBoxSettings)
//    fun checkBoxesSelectAll()
}