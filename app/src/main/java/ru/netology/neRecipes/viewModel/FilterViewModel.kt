package ru.netology.neRecipes.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ru.netology.neRecipes.adapter.FilterInteractionListener
import ru.netology.neRecipes.data.PrefsSettingsRepository
import ru.netology.neRecipes.data.impl.SettingsRepositoryImpl
import ru.netology.neRecipes.util.CheckBoxSettings

class FilterViewModel(
    application: Application
) : AndroidViewModel(application), FilterInteractionListener {

    private val repository: PrefsSettingsRepository = SettingsRepositoryImpl(application)

    val filterSet by repository::filtersSet

    var wasSettingsSetChangedFlag = false

    override var currentFilterSet = filterSet.value as MutableList

    private var checkedCategoriesCount = currentFilterSet.count { checkBoxSettings ->
        checkBoxSettings.isChecked
    }

    val selectAllState by repository::selectAllSettings

    override fun onItemClicked(checkBox: CheckBoxSettings) {
        if (!wasSettingsSetChangedFlag) wasSettingsSetChangedFlag = true
        currentFilterSet[checkBox.categoryId] = checkBox
        checkedCategoriesCount = currentFilterSet.count { checkBoxSettings ->
            checkBoxSettings.isChecked
        }
        val currentSelectAllState = checkedCategoriesCount == currentFilterSet.size
        Log.d("TAG", "view model fun currentSelectAllState $currentSelectAllState")
        if (selectAllState.value != currentSelectAllState)
        //val cb = checkBox.copy(isEnabled = checkBoxEnabled)
            repository.selectAllStateSave(currentSelectAllState)
        repository.checkBoxSave(checkBox)
        Log.d("TAG", "view model override var ")
    }

    fun updateFilterSetInRepository() {
        currentFilterSet.forEach { checkBox ->
            //Log.d("TAG", "view model fun updateFilterSetInRepository $checkBox")
            repository.checkBoxSave(checkBox)
        }
        //Log.d("TAG", "view model currentFilterSet $currentFilterSet")
    }

    fun selectAll() {
        currentFilterSet.forEachIndexed { index, checkBox ->
            //Log.d("TAG", "view model override var checkBoxEnabled =  $checkBoxEnabled")
            //repository.checkBoxSave(checkBox)
            if (!checkBox.isChecked) {
                val updatedCheckBox = CheckBoxSettings(
                    categoryId = checkBox.categoryId,
                    category = checkBox.category,
                    isChecked = true
                )
                repository.checkBoxSave(updatedCheckBox)
                currentFilterSet[index] = updatedCheckBox
            }
        }
        //checkBoxEnabled = true
        repository.checkBoxesSelectAll()

    }

}