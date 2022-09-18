package ru.netology.neRecipes.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.neRecipes.adapter.StepInterActionListener
import ru.netology.neRecipes.data.RecipeRepository
import ru.netology.neRecipes.data.Step
import ru.netology.neRecipes.data.impl.RecipeRepositoryImpl
import ru.netology.neRecipes.db.AppDb
import ru.netology.neRecipes.util.SingleLiveEvent

class StepViewModel(
    application: Application
) : AndroidViewModel(application), StepInterActionListener {

    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao,
        stepDao = AppDb.getInstance(
            context = application
        ).stepDao
    )

    val stepsList by repository::stepsList

    fun recipeSteps(recipeId: Long): List<Step> {
        return repository.recipeSteps(recipeId)
    }

    val navigateToStepCreateEdit = SingleLiveEvent<Int>()

    val currentStep = MutableLiveData<Step?>(null)

    var newImageUri: Uri? = null

    fun onSaveButtonClicked(
        stepDescription: String,
        hasCustomImage: Boolean,
        stepImageUri: Uri?
    ) {
        val step = currentStep.value?.copy(
            stepDescription = stepDescription,
            stepImageUri = stepImageUri
        ) ?: Step(
            id = RecipeRepository.NEW_STEP_ID,
            recipeId = RecipeRepository.NEW_RECIPE_ID,
            hasCustomImage = hasCustomImage,
            sequentialNumber = 1,
            stepDescription = stepDescription,
            stepImageUri = stepImageUri
        )
        Log.d("TAG", "step in onSaveButtonClicked = $step")
        repository.saveStep(step)
        currentStep.value = null
    }

    fun onAddClicked() {
        navigateToStepCreateEdit.call()
    }

    override fun onRemoveClicked(step: Step) = repository.deleteStep(step.id)

    override fun onEditClicked(step: Step) {
        currentStep.value = step
        navigateToStepCreateEdit.value = step.id
    }

}