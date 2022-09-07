package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.neRecipes.databinding.RecipeDescriptionDetailsFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel

class RecipeDescriptionDetailsFragment : Fragment() {
    private val model: RecipeViewModel by activityViewModels()
    private lateinit var binding: RecipeDescriptionDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeDescriptionDetailsFragmentBinding.inflate(inflater, container, false)
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

        fun newInstance() = RecipeDescriptionDetailsFragment()

    }
}