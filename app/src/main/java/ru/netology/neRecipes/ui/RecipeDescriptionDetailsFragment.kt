package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.databinding.RecipeDescriptionDetailsFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel

class RecipeDescriptionDetailsFragment () : Fragment() {
    private val model: RecipeViewModel by activityViewModels()

    //val currentRecipe =

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeDescriptionDetailsFragmentBinding.inflate(inflater, container, false).also { binding ->

        with(binding){
            val recipe = model.currentRecipe.value
            recipe?.let {
                title.text = it.title
                authorName.text = it.authorName
                recipeCategory.text = it.category
                recipeDescription.text = it.description
                recipeImageBlock.recipeImage.setImageURI(it.imageUri)
            }
        }
    }.root

    companion object {
        fun newInstance() = RecipeDescriptionDetailsFragment()
    }
}
