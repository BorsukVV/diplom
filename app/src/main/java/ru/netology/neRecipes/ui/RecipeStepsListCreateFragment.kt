package ru.netology.neRecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.adapter.StepsForEditAdapter
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.databinding.RecipeStepsListCreateFragmentBinding
import ru.netology.neRecipes.viewModel.StepViewModel

class RecipeStepsListCreateFragment() : Fragment() {

    private val stepViewModel: StepViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stepViewModel.navigateToStepCreateEdit.observe(this) { initialStepId ->
            val direction = RecipeTabFragmentDirections
                .fromRecipeTabFragmentToRecipeStepCreateFragment(initialStepId)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeStepsListCreateFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            val recipeID = arguments?.getLong("ID")
            recipeID?.let {
                Log.d("TAG", "*RecipeStepsListCreateFragment* initial id $recipeID")
                if (recipeID != RecipeRepository.NEW_RECIPE_ID) {
                    stepViewModel.recipeSteps(recipeID)
                    Log.d(
                        "TAG",
                        "*RecipeStepsListCreateFragment* initial id ${stepViewModel.stepsList.value}"
                    )
                }
            }
            val adapter = StepsForEditAdapter(stepViewModel)
            binding.createStepsRecyclerView.adapter = adapter

            stepViewModel.stepsList.observe(viewLifecycleOwner) { steps ->
                Log.d("TAG", "*RecipeStepsListCreateFragment* steps list size ${steps.size}")
                adapter.submitList(steps)
            }

            binding.fabAddStep.setOnClickListener {
                stepViewModel.onAddClicked()
            }

            val itemTouchHelper by lazy {


                val simpleCallback = object :
                    ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

                    override fun onMove(
                        p0: RecyclerView,
                        p1: RecyclerView.ViewHolder,
                        p2: RecyclerView.ViewHolder
                    ): Boolean {
                        val sourcePosition = p1.bindingAdapterPosition
                        val targetPosition = p2.bindingAdapterPosition
                        //Collections.swap(model.stepsList,sourcePosition,targetPosition)
                        adapter.notifyItemMoved(sourcePosition, targetPosition)
                        return true
                    }


                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        TODO("Not yet implemented")
                    }

                }

                ItemTouchHelper(simpleCallback)
            }

            itemTouchHelper.attachToRecyclerView(binding.createStepsRecyclerView)

        }.root


    companion object {

        fun newInstance(initialRecipeID: Long): RecipeStepsListCreateFragment {
            val args = Bundle()
            args.putLong("ID", initialRecipeID)
            val fragment = RecipeStepsListCreateFragment()
            fragment.arguments = args
            return fragment
        }

    }
}
