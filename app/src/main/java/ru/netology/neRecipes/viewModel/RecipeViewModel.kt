package ru.netology.neRecipes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.neRecipes.adapter.RecipeInterActionListener
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.data.impl.RecipeRepositoryImpl
import ru.netology.neRecipes.db.AppDb
import ru.netology.neRecipes.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInterActionListener {

    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao,
        pref = AppDb.getInstance(
            context = application
        ).stepDao
    )

    val data by repository::data

    val navigateToRecipeTabsForCreate = SingleLiveEvent<String>()

    val navigateToRecipeTabForDetails = SingleLiveEvent<Long>()

    val currentRecipe = MutableLiveData<Recipe?>(null)

    fun onSaveButtonClicked(content: String) {

        if (content.isBlank()) return

        val recipe = currentRecipe.value?.copy(
            description = content
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            title = "",
            authorName = "Red Badger",
            category = "",
            description = content,
            isFavourite = false,
            imageUrl = ""
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    fun onAddClicked() {
        navigateToRecipeTabsForCreate.call()
    }

    override fun chooseFavorite(recipe: Recipe) = repository.chooseFavorite(recipe.id)

    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.id)

    override fun onEditClicked(recipe: Recipe) {

        currentRecipe.value = recipe
        navigateToRecipeTabsForCreate.value = recipe.description
    }

    override fun onImageClicked(recipe: Recipe) {
        recipe.imageUrl?.let {
           //get new image from gallery
        }
    }

    override fun viewRecipeDetails(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeTabForDetails.value = recipe.id
    }

}