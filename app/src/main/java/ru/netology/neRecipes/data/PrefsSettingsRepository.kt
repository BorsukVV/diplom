package ru.netology.neRecipes.data

import androidx.lifecycle.MutableLiveData
import ru.netology.neRecipes.util.CheckBoxSettings

interface PrefsSettingsRepository {

    val filtersSet: MutableLiveData<List<CheckBoxSettings>>
    val selectAllSettings: MutableLiveData<Boolean>

    companion object {
        const val CATEGORY_FILTER = "CATEGORY_FILTER"
        const val PREF_FILTER_KEY = "PREF_FILTER_KEY"
        const val PREF_SELECT_ALL_KEY = "PREF_SELECT_ALL_KEY"
        const val PREF_DB_INDEXES = "PREF_DB_INDEXES"
    }

    fun checkBoxesSelectAll()
    fun checkBoxSelected(checkBox: CheckBoxSettings)
    fun checkBoxSave(checkBox: CheckBoxSettings)

    fun selectAllStateSave(state: Boolean)
}