package ru.netology.neRecipes.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.R
import ru.netology.neRecipes.databinding.RecipeDescriptionCreateFragmentBinding
import ru.netology.neRecipes.viewModel.RecipeViewModel
import ru.netology.neRecipes.viewModel.ViewUtil

class RecipeDescriptionCreateFragment : Fragment() {
    private val model: RecipeViewModel by activityViewModels()
    private lateinit var binding: RecipeDescriptionCreateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeDescriptionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            val originalRecipe = model.currentRecipe.value

            if (originalRecipe != null) {
                editTitle.setText(originalRecipe.title)
                editAuthor.setText(originalRecipe.authorName)
                categorySpinner.setSelection(originalRecipe.categorySpinnerPosition)
                editDescription.setText(originalRecipe.description)
                editDescription.requestFocus()
                recipeDescriptionImage.setImageURI(originalRecipe.imageUri)
            }

            val image = registerForActivityResult(ActivityResultContracts.OpenDocument()){
                requireActivity().contentResolver
                    .takePersistableUriPermission(requireNotNull(it), Intent.FLAG_GRANT_READ_URI_PERMISSION)
                model.newImageUri = it
                //Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
                recipeDescriptionImage.setImageURI(it)
            }

            recipeDescriptionImage.setOnClickListener {
                image.launch(arrayOf("image/*"))
            }

            ok.setOnClickListener {
                val title = this.editTitle.text.toString()
                val author = this.editAuthor.text.toString()
                val category = this.categorySpinner.selectedItem.toString()
                val categorySpinnerPosition = this.categorySpinner.selectedItemPosition
                val description = this.editDescription.text.toString()
                val hasCustomImage = model.newImageUri != null

                model.onSaveButtonClicked(
                    title = if (!title.isNullOrBlank())
                        title else resources.getString(R.string.default_recipe_title),
                    authorName = if (!author.isNullOrBlank())
                        author else resources.getString(R.string.default_recipe_author),
                    categorySpinnerPosition = categorySpinnerPosition,
                    category = category,
                    description = description,
                    hasCustomImage = hasCustomImage,
                    imageUri = if (hasCustomImage)
                        model.newImageUri else ViewUtil.descriptionImageTemplateUri(binding.root.resources)
                )
                //Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
                findNavController().navigateUp()
                model.newImageUri = null
            }
        }
    }

    companion object {

        fun newInstance() = RecipeDescriptionCreateFragment()

    }
}