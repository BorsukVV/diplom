package ru.netology.neRecipes.adapter


import ru.netology.neRecipes.data.Recipe

interface RecipeInterActionListener {
    fun chooseFavorite(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    //fun onImageClicked(recipe: Recipe)
    fun viewRecipeDetails(recipe: Recipe)
}