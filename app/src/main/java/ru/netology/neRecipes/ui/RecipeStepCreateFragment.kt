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
import ru.netology.neRecipes.databinding.RecipeStepCreateFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils
import ru.netology.neRecipes.viewModel.StepViewModel

class RecipeStepCreateFragment : Fragment() {

    private val model: StepViewModel by activityViewModels()
    private lateinit var binding: RecipeStepCreateFragmentBinding

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
                it ?: return@registerForActivityResult
                requireActivity().contentResolver
                    .takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                model.newImageUri = it
//                Log.d(
//                    "TAG",
//                    "*RecipeStepCreateFragment* *step.newImageUri = it*  ${model.newImageUri}"
//                )
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
                    else RecipeUtils.stepImageTemplateUri(binding.root.resources)
                )
//                Log.d(
//                    "TAG",
//                    "*RecipeStepCreateFragment* *onSaveButtonClicked* model.newImageUri = ${model.newImageUri}"
//                )
                findNavController().navigateUp()
                model.newImageUri = null
            }
        }
    }
}