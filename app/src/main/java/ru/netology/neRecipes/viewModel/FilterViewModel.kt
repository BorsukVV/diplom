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

    val filterSet by repository::filtersSetData

    var wasSettingsSetChangedFlag = false

    private var currentFilterSet = filterSet.value as MutableList

    var checkedCategoriesCount = currentFilterSet.count { checkBoxSettings ->
        checkBoxSettings.isChecked
    }

    val selectAllState by repository::selectAllSettings

    override fun onItemClicked(checkBox: CheckBoxSettings) {
        if (!wasSettingsSetChangedFlag) wasSettingsSetChangedFlag = true
        currentFilterSet[checkBox.categoryId] = checkBox
        checkedCategoriesCount = currentFilterSet.count { checkBoxSettings ->
            checkBoxSettings.isChecked
        }
        repository.selectAllStateSave(checkedCategoriesCount == currentFilterSet.size)
        repository.checkBoxSave(checkBox)
    }

    fun updateFilterSetInRepository() {
        currentFilterSet.forEach { checkBox ->
            Log.d("TAG", "view model fun updateFilterSetInRepository $checkBox")
            repository.checkBoxSave(checkBox)
        }
        Log.d("TAG", "view model currentFilterSet $currentFilterSet")
    }

    fun selectAll() {
//        currentFilterSet.forEachIndexed { index,  checkBox ->
//            Log.d("TAG", "view model fun updateFilterSetInRepository $checkBox")
//            //repository.checkBoxSave(checkBox)
//            if (!checkBox.isChecked) {
//                val updatedCheckBox = CheckBoxSettings(
//                    categoryId = checkBox.categoryId,
//                    category = checkBox.category,
//                    isChecked = true
//                )
//                repository.checkBoxSave(updatedCheckBox)
//                currentFilterSet[index] = updatedCheckBox
//            }
//
//        }
        repository.checkBoxesSelectAll()
        currentFilterSet = filterSet.value as MutableList<CheckBoxSettings>
        Log.d("TAG", "fun selectAll() $$$$$$ filterSet by repository::filtersSetData ${filterSet.value}")
        Log.d("TAG", "fun selectAll() view model &&&&& currentFilterSet $currentFilterSet")
    }

}