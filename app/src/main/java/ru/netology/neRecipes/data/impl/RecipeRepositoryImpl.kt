package ru.netology.neRecipes.data.impl

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

class RecipeRepositoryImpl(
    private val dao: RecipeDao,
    private val stepDao: StepDao

) : RecipeRepository {

    override fun getFilteredRecipes(categoryIndexes: List<Int>): LiveData<List<Recipe>> {
        return dao.getAll(categoryIndexes).map { entities ->
//            entities.forEach { recipeEntity -> println("Recipes(rep) recipe id " + recipeEntity.id) }
//            Log.d("TAG", "getFilteredRecipes categoryIndexes = $categoryIndexes")
            entities.map { it.toModel() }
        }
    }

    override fun getFilteredFavorites(categoryIndexes: List<Int>): LiveData<List<Recipe>> {
        return dao.getAllFavorites(categoryIndexes).map { entities ->
//            entities.forEach { recipeEntity -> println("Favorites(rep) recipe id " + recipeEntity.id) }
//            Log.d("TAG", "getFilteredFavorites categoryIndexes = $categoryIndexes")
            entities.map { it.toModel() }
        }
    }

    override fun getRecipeByID(id: Long) = dao.getRecipeByID(id).toModel()

    override fun recipeSteps(recipeID: Long): LiveData<List<Step>> {
        //Log.d("TAG", "RecipeRepositoryImpl recipeID = $recipeID")
        val stepEntities = stepDao.getRequiredRangeOfSteps(recipeID)
        //Log.d("TAG", "RecipeRepositoryImpl stepEntities = $stepEntities")
        val listOfSteps = stepEntities.map { entities ->
            entities.map { it.toModel() }
        }
        //Log.d("TAG", "RecipeRepositoryImpl listOfSteps = $listOfSteps")
        return listOfSteps
    }

    override fun save(recipe: Recipe): Long = dao.save(recipe.toEntity())

    override fun deleteStep(step: Step) {
        val isNewUnsavedStep =
            (step.id == RecipeRepository.NEW_STEP_ID && step.recipeId == RecipeRepository.NEW_RECIPE_ID)
        if (isNewUnsavedStep) {

            stepsList.value =
                stepsList.value?.filterNot { it.stepDescription == step.stepDescription }

        } else {
            stepDao.removeById(step.id)
        }
    }

    override fun saveStep(step: Step) = stepDao.save(step.toEntity())

    override fun associateStepsWithRecipe(newRecipeId: Long) {
        stepsList.value?.forEachIndexed { index, step ->
            saveStep(step.copy(recipeId = newRecipeId, sequentialNumber = (index + 1)))
        }
        stepsList.value = emptyList()
    }

    override fun chooseFavorite(recipeID: Long) = dao.chooseFavoriteById(recipeID)

    override fun delete(recipeID: Long) = dao.removeById(recipeID)

    override fun searchDatabase(searchQuery: String): LiveData<List<Recipe>> {
        return dao.searchDatabase(searchQuery).map { entities ->
            Log.d("TAG", "RecipeRepositoryImpl searchDatabase = $entities")
            entities.map {
                it.toModel()
            }
        }
    }

    companion object {

        val stepsList: MutableLiveData<List<Step>> = MutableLiveData(emptyList())

        fun addStepToList(step: Step): MutableLiveData<List<Step>> {
            val steps = checkNotNull(stepsList.value)
            stepsList.value =  steps + listOf(step)
            println(stepsList.value)
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