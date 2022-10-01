package ru.netology.neRecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.adapter.FilterAdapter
import ru.netology.neRecipes.databinding.FilterFragmentBinding
import ru.netology.neRecipes.viewModel.FilterViewModel

class FilterFragment : Fragment() {

    private lateinit var binding: FilterFragmentBinding

    private val filterViewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterFragmentBinding.inflate(layoutInflater, container, false)


        filterViewModel.selectAllState.value?.let {
            binding.selectAll.isChecked = it
            Log.d("TAG", "selectAll fun onCreateView $it")
            binding.selectAll.isEnabled = !binding.selectAll.isChecked
        }
        //Log.d("TAG", "filterViewModel.selectAllChecked.value fun onCreateView ${filterViewModel.selectAllState.value}")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            //Log.d("TAG", "filterSet ${filterViewModel.filterSet.value}")
            val adapter = FilterAdapter(filterViewModel)
            checkboxRecyclerView.adapter = adapter
            filterViewModel.filterSet.observe(viewLifecycleOwner) { checkBoxes ->

                Log.d("TAG", "RecyclerView filterSet $checkBoxes")
                adapter.submitList(checkBoxes)
            }

            filterViewModel.selectAllState.observe(viewLifecycleOwner){ isChecked ->
                if (selectAll.isChecked != isChecked) selectAll.isChecked = isChecked
                selectAll.isEnabled = !selectAll.isChecked
            }

            selectAll.setOnCheckedChangeListener { checkBox, checked ->
                checkBox.isEnabled = !checkBox.isChecked
                if (checked) filterViewModel.selectAll()
                //Log.d("TAG", "selectAll.setOnCheckedChangeListener 555")
                //Log.d("TAG", "selectAll.setOnCheckedChangeListener b parameter $checked")
            }
        }


    }

    override fun onPause() {
        super.onPause()
        filterViewModel.updateFilterSetInRepository()
        //filterViewModel.wasSettingsSetChangedFlag = false
        Log.d("TAG", "fun onPause()")
    }

}
