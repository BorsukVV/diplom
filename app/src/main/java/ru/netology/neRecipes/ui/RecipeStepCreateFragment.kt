package ru.netology.neRecipes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.neRecipes.databinding.RecipeStepCreateFragmentBinding
import ru.netology.neRecipes.viewModel.StepViewModel
import ru.netology.neRecipes.viewModel.ViewUtil

class RecipeStepCreateFragment : Fragment() {

    private val model: StepViewModel by activityViewModels()
    private lateinit var binding: RecipeStepCreateFragmentBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeStepCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val image = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
                requireActivity().contentResolver
                    .takePersistableUriPermission(
                        requireNotNull(it),
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                model.newImageUri = it
                Log.d("TAG", "*RecipeStepCreateFragment* *step.newImageUri = it*  ${model.newImageUri}")
                recipeStepDescriptionImage.setImageURI(it)
            }

            val originalStep = model.currentStep.value

            if (originalStep != null) {
                editStepDescription.setText(originalStep.stepDescription)
                editStepDescription.requestFocus()
                recipeStepDescriptionImage.setImageURI(originalStep.stepImageUri)
            }

            recipeStepDescriptionImage.setOnClickListener {
                image.launch(arrayOf("image/*"))
            }

            stepCreateOk.setOnClickListener {
                val stepDescription = this.editStepDescription.text.toString()
                val hasCustomImage = model.newImageUri != null

                model.onSaveButtonClicked(
                    stepDescription = stepDescription,
                    hasCustomImage = hasCustomImage,
                    stepImageUri = if (hasCustomImage) model.newImageUri
                        else ViewUtil.stepImageTemplateUri(binding.root.resources)

                )
                Log.d("TAG", "*RecipeStepCreateFragment* *onSaveButtonClicked* model.newImageUri = ${model.newImageUri}")
                findNavController().navigateUp()
                model.newImageUri = null
            }
        }
    }

//    companion object {
//
//        fun newInstance() =
//            RecipeStepCreateFragment()
//
//    }
}

//class RecipeDescriptionCreateFragment : Fragment() {
//    private val model: RecipeViewModel by activityViewModels()
//    private lateinit var binding: RecipeDescriptionCreateFragmentBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = RecipeDescriptionCreateFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(binding) {
//
//            val originalRecipe = model.currentRecipe.value
//
//            //TODO реализовать установку значения выпадающего списка
//
//            if (originalRecipe != null) {
//                editTitle.setText(originalRecipe.title)
//                editAuthor.setText(originalRecipe.authorName)
//                editDescription.setText(originalRecipe.description)
//                editDescription.requestFocus()
//                recipeDescriptionImage.setImageURI(originalRecipe.imageUri)
//            }
//
//            val image = registerForActivityResult(ActivityResultContracts.OpenDocument()){
//                model.newImageUri = it
//                Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
//                recipeDescriptionImage.setImageURI(it)
//            }
//
//            recipeDescriptionImage.setOnClickListener {
//                image.launch(arrayOf("image/*"))
//            }
//
//            val resources = binding.root.resources
//
//            val templateImageUri = Uri.Builder()
//                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//                .authority(resources.getResourcePackageName(R.drawable.recipe_image_template))
//                .appendPath(resources.getResourceTypeName(R.drawable.recipe_image_template))
//                .appendPath(resources.getResourceEntryName(R.drawable.recipe_image_template))
//                .build()
//
//            ok.setOnClickListener {
//                val title = this.editTitle.text.toString()
//                val author = this.editAuthor.text.toString()
//                val category = this.categorySpinner.selectedItem.toString()
//                val description = this.editDescription.text.toString()
//
//                model.onSaveButtonClicked(
//                    title = if (!title.isNullOrBlank())
//                        title else resources.getString(R.string.default_recipe_title),
//                    authorName = if (!author.isNullOrBlank())
//                        author else resources.getString(R.string.default_recipe_author),
//                    category = category,
//                    description = description,
//                    imageUri = if (model.newImageUri == null)
//                        templateImageUri else model.newImageUri
//
//                )
//                Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
//                findNavController().navigateUp()
//                model.newImageUri = null
//            }
//        }
//    }