package ru.netology.neRecipes.ui

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

            val originalRecipe = model.currentRecipe.value

            //TODO реализовать установку значения выпадающего списка

            if (originalRecipe != null) {
                editTitle.setText(originalRecipe.title)
                editAuthor.setText(originalRecipe.authorName)
                editDescription.setText(originalRecipe.description)
                editDescription.requestFocus()
                recipeDescriptionImage.setImageURI(originalRecipe.imageUri)
            }

            val image = registerForActivityResult(ActivityResultContracts.OpenDocument()){
                model.newImageUri = it
                Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
                recipeDescriptionImage.setImageURI(it)
            }

            recipeDescriptionImage.setOnClickListener {
                image.launch(arrayOf("image/*"))
            }

            val resources = binding.root.resources

            val templateImageUri = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.recipe_image_template))
                .appendPath(resources.getResourceTypeName(R.drawable.recipe_image_template))
                .appendPath(resources.getResourceEntryName(R.drawable.recipe_image_template))
                .build()

            ok.setOnClickListener {
                val title = this.editTitle.text.toString()
                val author = this.editAuthor.text.toString()
                val category = this.categorySpinner.selectedItem.toString()
                val description = this.editDescription.text.toString()

                model.onSaveButtonClicked(
                    title = if (!title.isNullOrBlank())
                        title else resources.getString(R.string.default_recipe_title),
                    authorName = if (!author.isNullOrBlank())
                        author else resources.getString(R.string.default_recipe_author),
                    category = category,
                    description = description,
                    imageUri = if (model.newImageUri == null)
                        templateImageUri else model.newImageUri

                )
                Log.d("TAG", "model.newImageUri = ${model.newImageUri}")
                findNavController().navigateUp()
                model.newImageUri = null
            }
        }
    }

//    fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
//        Uri.Builder()
//            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//            .authority(getResourcePackageName(resourceId))
//            .appendPath(getResourceTypeName(resourceId))
//            .appendPath(getResourceEntryName(resourceId))
//            .build()
//    }

    companion object {

        fun newInstance() = RecipeDescriptionCreateFragment()

    }
}