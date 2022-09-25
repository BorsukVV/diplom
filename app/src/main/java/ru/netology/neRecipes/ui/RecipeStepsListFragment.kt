package ru.netology.neRecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.adapter.StepsForViewAdapter
import ru.netology.neRecipes.databinding.RecipeStepsListFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel
import ru.netology.neRecipes.viewModel.StepViewModel

class RecipeStepsListFragment : Fragment() {
    private val recipeModel: RecipeViewModel by activityViewModels()
    private val stepModel: StepViewModel by activityViewModels()
    private lateinit var binding: RecipeStepsListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeStepsListFragmentBinding.inflate(inflater, container, false)
        val recipeID = arguments?.getLong("ID")
        recipeID?.let {
            with(binding) {
                val recipe = recipeModel.getRecipeByID(recipeID)
                Log.d("TAG", "StepsListFragment recipe from bundle $recipe")
                title.text = recipe.title
                stepModel.recipeSteps(recipeID)
                val adapter = StepsForViewAdapter(stepModel)
                stepsRecyclerView.adapter = adapter
                stepModel.stepsList.observe(viewLifecycleOwner) { steps ->
                    adapter.submitList(steps)
                }
            }
        }
        return binding.root
    }

    companion object {

        fun newInstance(initialRecipeID: Long): RecipeStepsListFragment {
            val args = Bundle()
            args.putLong("ID", initialRecipeID)
            val fragment = RecipeStepsListFragment()
            fragment.arguments = args
            return fragment
        }

    }
}
