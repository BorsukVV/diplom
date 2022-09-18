package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.databinding.RecipeListItemForCreateStepFragmentBinding



internal class StepsForEditAdapter(

    private val interActionListener: StepInterActionListener

) : ListAdapter<Step, StepsForEditAdapter.StepViewHolder>(DiffCallBack) {

    inner class StepViewHolder(
        private val binding: RecipeListItemForCreateStepFragmentBinding,
        listener: StepInterActionListener

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
            with(binding) {
                //TODO реализовать вывод порядкового номера шага
                recipeStepHeader.text = step.sequentialNumber.toString()
                recipeStepDescription.text = step.stepDescription
                recipeStepDescriptionImage.setImageURI(step.stepImageUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemForCreateStepFragmentBinding.inflate(inflater, parent, false)
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