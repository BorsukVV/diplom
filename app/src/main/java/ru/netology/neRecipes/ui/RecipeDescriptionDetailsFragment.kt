package ru.netology.neRecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.databinding.RecipeDescriptionDetailsFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel

class RecipeDescriptionDetailsFragment : Fragment() {
    private val model: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeDescriptionDetailsFragmentBinding.inflate(inflater, container, false)
        .also { binding ->
            val recipeID = arguments?.getLong("ID")
            Log.d("TAG", "*RecipeDescriptionDetailsFragment* initial id $recipeID")

            recipeID?.let {
                with(binding) {
                    val recipe = model.getRecipeByID(recipeID)
                    recipe.let {
                        title.text = it.title
                        authorName.text = it.authorName
                        recipeCategory.text = it.category
                        recipeDescription.text = it.description
                        recipeImageBlock.imageGroup.visibility =
                            if (recipe.hasCustomImage) View.VISIBLE else View.GONE
                        if (it.imageUri != null) {
                            if (it.hasCustomImage) recipeImageBlock.recipeImage.setImageURI(it.imageUri)
                        }
                    }
                    recipeImageBlock.isFavouriteIcon.setOnClickListener {
                        model.chooseFavorite(recipe)
                    }
                }
            }
        }.root

    companion object {
        fun newInstance(initialRecipeID: Long): RecipeDescriptionDetailsFragment {
            val args = Bundle()
            args.putLong("ID", initialRecipeID)
            val fragment = RecipeDescriptionDetailsFragment()
            fragment.arguments = args
            Log.d(
                "TAG",
                "*RecipeDescriptionDetailsFragment* fragment.arguments ${fragment.arguments}"
            )
            return fragment
        }
    }
}
