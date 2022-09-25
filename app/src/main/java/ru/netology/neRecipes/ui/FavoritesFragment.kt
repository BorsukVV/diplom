package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.adapter.RecipesAdapter
import ru.netology.neRecipes.databinding.FavoritesFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils
import ru.netology.neRecipes.viewModel.RecipeViewModel

class FavoritesFragment : Fragment() {
    private val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigateToRecipeTabForDetails.observe(this) { initialRecipeID ->
            val direction = FavoritesFragmentDirections.fromFavoritesToDetailsTab(
                initialRecipeID,
                RecipeUtils.EDIT
            )
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavoritesFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)

        binding.favouritesRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val favorites = recipes.filter { it.isFavourite }
            adapter.submitList(favorites)
        }

    }.root


}


