package ru.netology.neRecipes.data.impl

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.db.RecipeDao
import ru.netology.neRecipes.db.RecipeEntity
import ru.netology.neRecipes.db.StepDao
import ru.netology.neRecipes.db.StepEntity

private const val CATEGORY_FILTER = "CATEGORY_FILTER"

class RecipeRepositoryImpl(
    private val dao: RecipeDao,
    private val stepDao: StepDao,
    application: Application

) : RecipeRepository {

    private val filters = application.getSharedPreferences(
        CATEGORY_FILTER, Context.MODE_PRIVATE
    )

//    private var filterSet: Long by Delegates.observable(
//        filters.getLong(NEXT_ID_PREF_KEY, 0L)
//    ) {_,_, newValue ->
//        prefs.edit { putLong(NEXT_ID_PREF_KEY, newValue) }
//    }


    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getRecipeByID(id: Long) = dao.getRecipeByID(id).toModel()

    //fun steps() = println(stepDao.getAllSteps().value)

    override val favorites = dao.getAllFavorites().map { entities ->
        entities.map { it.toModel() }
    }

    override fun recipeSteps (recipeID: Long): LiveData<List<Step>> {
        Log.d("TAG", "RecipeRepositoryImpl recipeID = $recipeID")
        val stepEntities = stepDao.getRequiredRangeOfSteps(recipeID)
        Log.d("TAG", "RecipeRepositoryImpl stepEntities = $stepEntities")
        val listOfSteps = stepEntities.map { entities ->
            entities.map { it.toModel() }
        }
        Log.d("TAG", "RecipeRepositoryImpl listOfSteps = $listOfSteps")
        return listOfSteps
    }

    override fun save(recipe: Recipe): Long = dao.save(recipe.toEntity())

    override fun deleteStep(stepID: Long) = stepDao.removeById(stepID)

    override fun saveStep(step: Step) = stepDao.save(step.toEntity())

    override fun associateStepsWithRecipe(newRecipeId: Long) {
        stepsList.value?.forEachIndexed { index, step ->
            saveStep(step.copy(recipeId = newRecipeId, sequentialNumber = (index + 1)))
        }
        stepsList.value = emptyList()
    }

    override fun chooseFavorite(recipeID: Long) = dao.chooseFavoriteById(recipeID)

    override fun delete(recipeID: Long) = dao.removeById(recipeID)

    companion object{

        val stepsList: MutableLiveData<List<Step>> = MutableLiveData(emptyList())

        fun addStepToList(step: Step): MutableLiveData<List<Step>> {
            val steps = checkNotNull(stepsList.value)
            stepsList.value = listOf(step) + steps
            return stepsList
        }
    }

}
//region externalFunctions

private fun Recipe.toEntity() = RecipeEntity(
    id = id,
    title = title,
    authorName = authorName,
    categorySpinnerPosition = categorySpinnerPosition,
    category = category,
    description = description,
    isFavourite = isFavourite,
    hasCustomImage = hasCustomImage,
    recipeImageUri = imageUri.toString()
)

private fun RecipeEntity.toModel() = Recipe(
    id = id,
    title = title,
    authorName = authorName,
    categorySpinnerPosition = categorySpinnerPosition,
    category = category,
    description = description,
    isFavourite = isFavourite,
    hasCustomImage = hasCustomImage,
    imageUri = Uri.parse(recipeImageUri)
)

private fun Step.toEntity() = StepEntity(
    id = id,
    recipeId = recipeId,
    stepDescription = stepDescription,
    hasCustomImage = hasCustomImage,
    sequentialNumber = sequentialNumber,
    stepImageUri = stepImageUri.toString()
)

private fun StepEntity.toModel() = Step(
    id = id,
    recipeId = recipeId,
    stepDescription = stepDescription,
    hasCustomImage = hasCustomImage,
    sequentialNumber = sequentialNumber,
    stepImageUri = Uri.parse(stepImageUri)
)

//endregion