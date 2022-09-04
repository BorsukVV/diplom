package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.adapter.RecipesAdapter
import ru.netology.neRecipes.databinding.FeedFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel

open class FeedFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setFragmentResultListener(
//            requestKey = RecipeCreationFragment.REQUEST_KEY
//        ) { requestKey, bundle ->
//            if (requestKey != RecipeCreationFragment.REQUEST_KEY) return@setFragmentResultListener
//            val newPostContent = bundle.getString(
//                RecipeCreationFragment.RESULT_KEY
//            ) ?: return@setFragmentResultListener
//            viewModel.onSaveButtonClicked(newPostContent)
//        }

        viewModel.navigateToRecipeTabsForCreate.observe(this) { initialContent ->
            val direction = FeedFragmentDirections.fromFeedFragmentToRecipeTabFragment(initialContent)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeTabForDetails.observe(this) { initialContent ->
            val direction = FeedFragmentDirections.fromFeedFragmentToRecipeTabFragment(
                initialContent.toString()
            )
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)

        binding.recipesRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fabAddRecipe.setOnClickListener {
            viewModel.onAddClicked()
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