package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.adapter.RecipesAdapter
import ru.netology.neRecipes.databinding.MainListFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils
import ru.netology.neRecipes.viewModel.RecipeViewModel

open class MainListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val model: RecipeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.navigateToRecipeTabsForCreate.observe(this) { initialRecipeID ->
            val direction = MainListFragmentDirections
                .fromMainListFragmentToRecipeTabFragment(
                    initialRecipeID = initialRecipeID,
                    operationCode = RecipeUtils.CREATE
                )
            findNavController().navigate(direction)
        }

        model.navigateToRecipeTabForDetails.observe(this) { initialRecipeID ->
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

        val adapter = RecipesAdapter(model)

        binding.recipesRecyclerView.adapter = adapter

        model.data.observe(viewLifecycleOwner) { recipes ->
            //Log.d("TAG", "recipes size ${recipes.size}")
            binding.defaultStubGroup.visibility =
                if (recipes.isNotEmpty()) View.GONE else View.VISIBLE
            adapter.submitList(recipes)
        }

        binding.fabAddRecipe.setOnClickListener {
            model.onAddClicked()
        }

    }.root

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//
//        val search = menu?.findItem(R.id.menu_search)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//        // after this you need implement your setOnQueryTextListener in main activity for pass 'this'
//        //for implement just go above and add it to last like this
//        //  class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener
//
//        return true
//    }
//    //after that just alt + enter after moving your cursor to the warning shown
//    // and then implement these two functions
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        //it will triggered when we submit the written test
//        return true
//    }
//    // this function will triggered when we write even a single char in search view
//    override fun onQueryTextChange(query: String?): Boolean {
//        if(query != null){
//            searchDatabase(query)
//        }
//        return true
//    }
//    // I have just created this function for searching our database
//    private fun searchDatabase(query: String) {
//        // %" "% because our costume sql query will require that
//        val searchQuery = "%$query%"
//
//        mainViewModel.searchDatabase(searchQuery).observe(this, { list ->
//            list.let {
//                myAdapter.setData(it)
//            }
//        })
//    }

}