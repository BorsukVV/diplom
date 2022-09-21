package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.databinding.FilterFragmentBinding
import ru.netology.neRecipes.viewModel.StepViewModel

class FilterFragment : Fragment() {

    private val model: StepViewModel by activityViewModels()
    private lateinit var binding: FilterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        with(binding){
//            checkboxGroup.findViewById<CheckBox>(asian.id).isChecked {
//            }
//        }
    }

}
