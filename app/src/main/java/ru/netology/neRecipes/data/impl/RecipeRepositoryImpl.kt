package ru.netology.neRecipes.data.impl

import android.net.Uri
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

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }
//    override var stepsList: MutableLiveData<List<Step>> = checkNotNull(stepsList.value) {"Error. Data is null"}
//        set(value) {}

    fun getRecipeByID(recipeID: Long) = data.value?.filter {it.id == recipeID}

    override var stepsList = stepDao.getUndistributedSteps().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(recipe: Recipe) = dao.save(recipe.toEntity())

    override fun deleteStep(stepID: Int) = stepDao.removeById(stepID)

    override fun saveStep(step: Step) = stepDao.save(step.toEntity())

    override fun chooseFavorite(recipeID: Long) = dao.chooseFavoriteById(recipeID)

    override fun delete(recipeID: Long) = dao.removeById(recipeID)

//    override fun insertStepToBuffer(step: Step) {
//
//        stepsList.value?.add(step)
//
//    }

}

private fun Recipe.toEntity() = RecipeEntity(
    id = id,
    title = title,
    authorName = authorName,
    category = category,
    description = description,
    isFavourite = isFavourite,
    recipeImageUri = imageUri.toString()
)

private fun RecipeEntity.toModel() = Recipe(
    id = id,
    title = title,
    authorName = authorName,
    category = category,
    description = description,
    isFavourite = isFavourite,
    imageUri = Uri.parse(recipeImageUri)
)

private fun Step.toEntity() = StepEntity(
    id = id,
    recipeId = recipeId,
    stepDescription = stepDescription,
    stepImageUri = stepImageUri.toString()
)

private fun StepEntity.toModel() = Step(
    id = id,
    recipeId = recipeId,
    stepDescription = stepDescription,
    stepImageUri = Uri.parse(stepImageUri)
)