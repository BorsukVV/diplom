package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.databinding.RecipeListItemForCreateStepFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils

internal class StepsForEditAdapter(

    private val interactionListener: StepInteractionListener

) : ListAdapter<Step, StepsForEditAdapter.StepViewHolder>(DiffCallBack) {

    private var currentRecipeStepsList = mutableListOf<Step>()

    inner class StepViewHolder(
        private val binding: RecipeListItemForCreateStepFragmentBinding,
        listener: StepInteractionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var step: Step

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(step)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(step)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.options.setOnClickListener {
                popupMenu.show()
            }
        }

        fun bind(step: Step) {
            this.step = step
            with(binding) {
                recipeStepHeader.text = step.sequentialNumber.toString()
                recipeStepDescription.text = step.stepDescription
                imageGroup.visibility =
                    if (step.hasCustomImage) View.VISIBLE else View.GONE

                if (step.stepImageUri != null) {
                    recipeStepDescriptionImage.setImageURI(step.stepImageUri)
                } else {
                    RecipeUtils.stepImageTemplateUri(binding.root.resources)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemForCreateStepFragmentBinding.inflate(inflater, parent, false)
        return StepViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

//    override fun submitList(list: List<Step>?) {
//        super.submitList(list)
//        if (list != null) {
//            currentRecipeStepsList = list as MutableList<Step>
//        }
//
//    }

    fun getStep(position: Int): Step {
        return currentRecipeStepsList[position]
    }


    private object DiffCallBack : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem == newItem
    }

}