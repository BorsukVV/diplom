package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.databinding.RecipeListItemStepFragmentBinding



internal class StepsForViewAdapter(

    private val interActionListener: StepInterActionListener

) : ListAdapter<Step, StepsForViewAdapter.StepViewHolder>(DiffCallBack) {

    inner class StepViewHolder(
        private val binding: RecipeListItemStepFragmentBinding,
        listener: StepInterActionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var step: Step

        fun bind(step: Step) {

            val resources = binding.root.resources
            val stepTitleStringTemplate = resources.getString(R.string.step_with_counter)

            with(binding) {
                recipeStepHeader.text = String.format(stepTitleStringTemplate, step.sequentialNumber)
                recipeStepDescription.text = step.stepDescription
                recipeStepDescriptionImageGroup.visibility = if (step.hasCustomImage) View.VISIBLE else View.GONE
                recipeStepDescriptionImage.setImageURI(step.stepImageUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemStepFragmentBinding.inflate(inflater, parent, false)
        return StepViewHolder(binding, interActionListener)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem == newItem
    }

}