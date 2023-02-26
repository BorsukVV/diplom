package ru.netology.neRecipes.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.neRecipes.adapter.RecipeInteractionListener
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.data.impl.RecipeRepositoryImpl
import ru.netology.neRecipes.data.impl.SettingsRepositoryImpl
import ru.netology.neRecipes.db.AppDb
import ru.netology.neRecipes.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao,
        stepDao = AppDb.getInstance(
            context = application
        ).stepDao
    )

    private val filters = SettingsRepositoryImpl.getInstance(application)

    private val categoryFilters by filters::categoryIndexesForDBRequest

    var data: LiveData<List<Recipe>> = Transformations.switchMap(categoryFilters){filterList ->
        //Log.d("TAG", "categoryFilters in RecipeViewModel = $it")
        repository.getFilteredRecipes(filterList)
    }

    var favoriteRecipes: LiveData<List<Recipe>> = Transformations.switchMap(categoryFilters){filterList ->
        //Log.d("TAG", "categoryFilters in RecipeViewModel = $it")
        repository.getFilteredRecipes(filterList)
    }

    val navigateToRecipeTabsForCreate = SingleLiveEvent<Long>()

    val navigateToRecipeTabForDetails = SingleLiveEvent<Long>()

    val currentRecipe = MutableLiveData<Recipe?>(null)

    var newImageUri: Uri? = null

    fun onSaveButtonClicked(
        title: String,
        authorName: String,
        categorySpinnerPosition: Int,
        category: String,
        description: String,
        hasCustomImage: Boolean,
        imageUri: Uri?
    ) {

        if (description.isBlank()) return

        val recipe = currentRecipe.value?.copy(
            title = title,
            authorName = authorName,
            categorySpinnerPosition = categorySpinnerPosition,
            category = category,
            description = description,
            hasCustomImage = hasCustomImage,
            imageUri = imageUri
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            title = title,
            authorName = authorName,
            categorySpinnerPosition = categorySpinnerPosition,
            category = category,
            description = description,
            isFavourite = false,
            hasCustomImage = hasCustomImage,
            imageUri = imageUri
        )
        val newRecipeId = repository.save(recipe)
        Log.d("TAG", "last recipeId in DATABASE = $newRecipeId")
        currentRecipe.value = null
        repository.associateStepsWithRecipe(newRecipeId)

    }

    fun getRecipeByID(recipeID: Long) = repository.getRecipeByID(recipeID)


    fun onAddClicked() {
        navigateToRecipeTabsForCreate.value = RecipeRepository.NEW_RECIPE_ID
    }

    override fun chooseFavorite(recipe: Recipe) = repository.chooseFavorite(recipe.id)

    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.id)

    override fun onEditClicked(recipe: Recipe) {

        currentRecipe.value = recipe
        navigateToRecipeTabsForCreate.value = recipe.id
    }

    override fun viewRecipeDetails(recipe: Recipe) {
        navigateToRecipeTabForDetails.value = recipe.id
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Recipe>> {
        data = repository.searchDatabase(searchQuery)
        Log.d("TAG", "searchDatabase in RecipeViewModel = ${data.value}")
        return data
    }

}