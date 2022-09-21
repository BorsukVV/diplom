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
    ): View? {
        binding = RecipeStepsListFragmentBinding.inflate(inflater, container, false)
        with(binding){
            val recipe = recipeModel.currentRecipe.value
            recipe?.let {
                title.text = it.title
                val recipeSteps = stepModel.recipeSteps(it.id)
                Log.d("TAG", "steps list StepsListFragment $recipeSteps")
//                stepModel.stepsList = stepModel.recipeSteps(it.id)
                val adapter = StepsForViewAdapter(stepModel)
                stepsRecyclerView.adapter = adapter
//                adapter.submitList(recipeSteps)
                stepModel.stepsList.observe(viewLifecycleOwner) {
                    val steps = stepModel.recipeSteps(recipe.id)
                    Log.d("TAG", "steps list size StepsListFragment ${steps.size}")
                    adapter.submitList(steps)
                }

            }

        }
        return binding.root
    }

    companion object {

        fun newInstance() = RecipeStepsListFragment()

    }
}