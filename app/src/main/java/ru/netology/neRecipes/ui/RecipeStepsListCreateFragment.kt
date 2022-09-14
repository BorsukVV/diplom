package ru.netology.neRecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.adapter.StepsForEditAdapter
import ru.netology.neRecipes.databinding.RecipeStepsListCreateFragmentBinding
import ru.netology.neRecipes.viewModel.StepViewModel

class RecipeStepsListCreateFragment : Fragment() {

    private val model: StepViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.navigateToStepCreateEdit.observe(this) { initialContent  ->
            val direction = RecipeStepsListCreateFragmentDirections.
            actionRecipeStepsListCreateFragmentToRecipeStepCreateFragment(initialContent.toString())
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeStepsListCreateFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = StepsForEditAdapter(model)

        binding.createStepsRecyclerView.adapter = adapter

        model.stepsList.observe(viewLifecycleOwner) { steps ->
            Log.d("TAG", "recipes size ${steps.size}")
            adapter.submitList(steps)
        }

        binding.fabAddStep.setOnClickListener {
            model.onAddClicked()
        }

    }.root

    companion object {

        fun newInstance() = RecipeStepsListCreateFragment()

    }
}