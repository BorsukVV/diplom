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
        ).stepDao,
        application
    )

//    val stepsList: MutableLiveData<List<Step>> = MutableLiveData(emptyList())

//    fun recipeSteps() = steps
//
//    private val steps
//        get() = checkNotNull(stepsList.value) {
//            "Error. Data is null"
//        }

    val stepsList = repository.stepsList

    fun recipeSteps(recipeId: Long): List<Step> {
        Log.d("TAG", "step in onSaveButtonClicked = ${repository.recipeSteps(recipeId)}")
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
        repository.addStepToList(step)
        currentStep.value = null
    }

//    private fun insert(step: Step) {
//        stepsList.value = listOf(step) + steps
//
//    }

    fun onAddClicked() {
        navigateToStepCreateEdit.call()
    }

    override fun onRemoveClicked(step: Step) = repository.deleteStep(step.id)

    override fun onEditClicked(step: Step) {
        currentStep.value = step
        navigateToStepCreateEdit.value = step.id
    }

}