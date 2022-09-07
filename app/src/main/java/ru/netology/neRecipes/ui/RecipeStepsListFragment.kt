package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.databinding.RecipeStepsListFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel


class RecipeStepsListFragment : Fragment() {
    private val model: RecipeViewModel by activityViewModels()
    private lateinit var binding: RecipeStepsListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeStepsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        model.  .observe(viewLifecycleOwner){
//            binding.tvinfo.text = it.info
//
//        }
    }
    companion object {

        fun newInstance() = RecipeStepsListFragment()

    }
}