package ru.netology.neRecipes.data.impl

import android.net.Uri
import android.util.Log
import androidx.lifecycle.map
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.db.RecipeDao
import ru.netology.neRecipes.db.RecipeEntity
import ru.netology.neRecipes.db.StepDao
import ru.netology.neRecipes.db.StepEntity

class RecipeRepositoryImpl(
    private val dao: RecipeDao,
    private val stepDao: StepDao,
    //application: Application

) : RecipeRepository {

//            run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            preferences.edit {
//                putString("key", "value")
//            }
//        }
//
//        run {
//            val preferences = getPreferences(Context.MODE_PRIVATE)
//            val value = preferences.getString("key", "no value") ?: return@run
//            Snackbar.make(binding.root, value, Snackbar.LENGTH_INDEFINITE).show()
//        }

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    fun getRecipeByID(id: Long) = dao.getRecipeByID(id).toModel()


    override val favorites = dao.getAllFavorites().map { entities ->
        entities.map { it.toModel() }
    }

    override var stepsList = stepDao.getRequiredRangeOfSteps(RecipeRepository.NEW_RECIPE_ID).map { entities ->
        entities.map { it.toModel() }
    }

    override fun recipeSteps(recipeID: Long): List<Step> {
        return stepDao.getRequiredRangeOfSteps(recipeID).value?.map {
            it.toModel() } ?: emptyList()
        }


    override fun save(recipe: Recipe) = dao.save(recipe.toEntity())

    override fun deleteStep(stepID: Int) = stepDao.removeById(stepID)

    override fun saveStep(step: Step) = stepDao.save(step.toEntity())

    override fun associateStepsWithRecipe() {
        val recipeId = data.value?.last()?.id
        Log.d("TAG", "last recipeId in associateStepsWithRecipe = $recipeId")
        stepsList.value?.let { steps ->
            for (step in steps) {
                if (recipeId != null) {
                    val associatedStep = step.copy(recipeId = recipeId)
                    saveStep(associatedStep)
                }
            }
        }
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