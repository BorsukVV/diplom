package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.R
import ru.netology.neRecipes.adapter.RecipesAdapter
import ru.netology.neRecipes.databinding.MainListFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils
import ru.netology.neRecipes.viewModel.RecipeViewModel

open class MainListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    private val adapter: RecipesAdapter by lazy { RecipesAdapter(recipeViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeViewModel.navigateToRecipeTabsForCreate.observe(this) { initialRecipeID ->
            val direction = MainListFragmentDirections
                .fromMainListFragmentToRecipeTabFragment(
                    initialRecipeID = initialRecipeID,
                    operationCode = RecipeUtils.CREATE
                )
            findNavController().navigate(direction)
        }

        recipeViewModel.navigateToRecipeTabForDetails.observe(this) { initialRecipeID ->
            val direction = MainListFragmentDirections
                .fromMainListFragmentToRecipeTabFragment(
                    initialRecipeID = initialRecipeID,
                    operationCode = RecipeUtils.EDIT
                )
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainListFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.recipesRecyclerView.adapter = adapter

        recipeViewModel.data.observe(viewLifecycleOwner) { recipes ->
            //Log.d("TAG", "recipes size ${recipes.size}")
            //recipes.forEach { recipe -> println("Recipes(fragment) recipe id " + recipe.id) }
            binding.defaultStubGroup.visibility =
                if (recipes.isNotEmpty()) View.GONE else View.VISIBLE
            adapter.submitList(recipes)
        }

        binding.fabAddRecipe.setOnClickListener {
            recipeViewModel.onAddClicked()
        }

        //recipeViewModel.favoriteRecipes.observe(viewLifecycleOwner){}

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search?.actionView as? SearchView
                searchView?.apply {
                    isSubmitButtonEnabled = true
                    setOnQueryTextListener(this@MainListFragment)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
   }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //it will triggered when we submit the written test
        return true
    }
    // this function will triggered when we write even a single char in search view
    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }
    // I have just created this function for searching our database
    private fun searchDatabase(query: String) {
        // %" "% because our costume sql query will require that
        val searchQuery = "%$query%"

        recipeViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

}