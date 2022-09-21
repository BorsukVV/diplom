package ru.netology.neRecipes.data.impl

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
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

    fun getRecipeByID(id: Long) = dao.getRecipeByID(id).toModel()

    fun steps() = println(stepDao.getAllSteps().value)


    override val favorites = dao.getAllFavorites().map { entities ->
        entities.map { it.toModel() }
    }

//    override var stepsList = stepDao.getRequiredRangeOfSteps(RecipeRepository.NEW_RECIPE_ID).map { entities ->
//        entities.map { it.toModel() }
//    }

    override var stepsList: MutableLiveData<List<Step>> = MutableLiveData(emptyList())

    private val steps
        get() = checkNotNull(stepsList.value) {
            "Error. Data is null"
        }

    override fun recipeSteps(recipeID: Long): List<Step> {
        return stepDao.getRequiredRangeOfSteps(recipeID).value?.map {
            it.toModel() } ?: emptyList()
        }

    override fun save(recipe: Recipe): Long = dao.save(recipe.toEntity())

    override fun deleteStep(stepID: Int) = stepDao.removeById(stepID)

    override fun saveStep(step: Step) = stepDao.save(step.toEntity())

    override fun associateStepsWithRecipe(newRecipeId: Long) {
        val recipeSteps = stepsList.value
        Log.d("TAG", "step list in repo before saving = $recipeSteps")
        if (recipeSteps != null) {
            for (i in recipeSteps.indices) {
                saveStep(recipeSteps[i].copy(recipeId = newRecipeId, sequentialNumber = i))
            }
        }
steps()
//        val recipeId = data.value?.last()?.id
//        Log.d("TAG", "last recipeId in associateStepsWithRecipe = $recipeId")
//        stepsList.value?.let { steps ->
//            for (step in steps) {
//                if (recipeId != null) {
//                    val associatedStep = step.copy(recipeId = recipeId)
//                    saveStep(associatedStep)
//                }
//            }
//        }
    }

    override fun addStepToList(step: Step) {
        stepsList.value = listOf(step) + steps
    }

    override fun chooseFavorite(recipeID: Long) = dao.chooseFavoriteById(recipeID)

    override fun delete(recipeID: Long) = dao.removeById(recipeID)

}

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