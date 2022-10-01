package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.databinding.FilterItemFragmentBinding
import ru.netology.neRecipes.util.CheckBoxSettings

internal class FilterAdapter(

    private val interactionListener: FilterInteractionListener

) : ListAdapter<CheckBoxSettings, FilterAdapter.FilterViewHolder>(DiffCallBack) {

    inner class FilterViewHolder(
        private val binding: FilterItemFragmentBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(checkBox: CheckBoxSettings) {
            with(binding) {
                filterCheckBox.isChecked = checkBox.isChecked
                filterCheckBox.text = checkBox.category
                filterCheckBox.setOnCheckedChangeListener { _, checkBoxState ->
                    val changedCheckBox = CheckBoxSettings(
                        categoryId = checkBox.categoryId,
                        category = checkBox.category,
                        isChecked = checkBoxState
                    )
                    interactionListener.onItemClicked(changedCheckBox)
                    //Log.d("TAG", "checkbox state? ${checkBox.categoryId} $b")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterItemFragmentBinding.inflate(inflater, parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<CheckBoxSettings>() {
        override fun areItemsTheSame(oldItem: CheckBoxSettings, newItem: CheckBoxSettings): Boolean =
            oldItem.categoryId == newItem.categoryId

        override fun areContentsTheSame(oldItem: CheckBoxSettings, newItem: CheckBoxSettings): Boolean =
            oldItem.categoryId == newItem.categoryId
    }

}