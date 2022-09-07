package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.databinding.RecipeDescriptionCreateFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel

class RecipeDescriptionCreateFragment : Fragment() {
    private val model: RecipeViewModel by activityViewModels()
    private lateinit var binding: RecipeDescriptionCreateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeDescriptionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val title = this.editTitle.text.toString()
            val author = this.editAuthor.text.toString()
            val category = this.categorySpinner.selectedItem.toString()
            val description = this.editDescription.text.toString()

            ok.setOnClickListener {
                model.onSaveButtonClicked(
                    title = title,
                    authorName = author,
                    category = category,
                    description = description
                )
                findNavController().navigateUp()
            }
        }

    }

    companion object {

        fun newInstance() = RecipeDescriptionCreateFragment()

    }
}