package ru.netology.neRecipes.adapter

//import android.util.Log
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
        private val binding: FilterItemFragmentBinding,
        listener: FilterInteractionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var checkBox: CheckBoxSettings

        init {
            binding.filterCheckBox.setOnCheckedChangeListener { _, checkBoxState ->

                val changedCheckBox = CheckBoxSettings(
                    categoryId = checkBox.categoryId,
                    category = checkBox.category,
                    isChecked = checkBoxState
                )
                listener.onItemClicked(changedCheckBox)
            }
        }

        fun bind(checkBox: CheckBoxSettings) {

            //Log.d("TAG", "adapter fun bind(checkBox: CheckBoxSettings)  $checkBox ")

            this.checkBox = checkBox
            //Log.d("TAG", "adapter this.checkBox ${this.checkBox} ")

            with(binding) {
                filterCheckBox.isChecked = checkBox.isChecked
                filterCheckBox.text = checkBox.category
                filterCheckBox.isEnabled = checkBox.isEnabled
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterItemFragmentBinding.inflate(inflater, parent, false)
        return FilterViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<CheckBoxSettings>() {
        override fun areItemsTheSame(
            oldItem: CheckBoxSettings,
            newItem: CheckBoxSettings
        ): Boolean =
            oldItem.categoryId == newItem.categoryId

        override fun areContentsTheSame(
            oldItem: CheckBoxSettings,
            newItem: CheckBoxSettings
        ): Boolean =
            oldItem == newItem
    }

}