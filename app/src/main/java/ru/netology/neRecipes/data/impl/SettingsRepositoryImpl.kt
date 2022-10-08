package ru.netology.neRecipes.data.impl

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.PrefsSettingsRepository
import ru.netology.neRecipes.util.CheckBoxSettings

class SettingsRepositoryImpl(
    application: Application
) : PrefsSettingsRepository {

    //get preferences
    private val filters = application.getSharedPreferences(
        PrefsSettingsRepository.CATEGORY_FILTER, Context.MODE_PRIVATE
    )

    private val checkBoxesNames: Array<String>

    override val filtersSet: MutableLiveData<List<CheckBoxSettings>>

    override val selectAllSettings: MutableLiveData<Boolean>

    private var selectAllSettingsValue
        get() = checkNotNull(selectAllSettings.value) {
            "Error. SELECT_ALL is null"
        }
        set(value) {
            filters.edit {
                putBoolean(PrefsSettingsRepository.PREF_SELECT_ALL_KEY, value)
            }
            selectAllSettings.value = value
        }

    private var filtersSetValue
        get() = checkNotNull(filtersSet.value) {
            "Error. Filters is null"
        }
        set(value) {
            filters.edit {
                val serializedCheckBoxSettings = Json.encodeToString(value)
                putString(PrefsSettingsRepository.PREF_FILTER_KEY, serializedCheckBoxSettings)
            }
            filtersSet.value = value
        }

    init {
        checkBoxesNames = application.resources.getStringArray(R.array.category_list)
        val categoriesCount = checkBoxesNames.size
        val serializedCheckBoxSettings =
            filters.getString(PrefsSettingsRepository.PREF_FILTER_KEY, null)

        val filtersSet: List<CheckBoxSettings> = if (serializedCheckBoxSettings != null) {
            Json.decodeFromString(serializedCheckBoxSettings)
        } else {
            List(categoriesCount) { index ->
                CheckBoxSettings(
                    categoryId = index,
                    category = checkBoxesNames[index]
                )
            }
        }
        this.filtersSet = MutableLiveData(filtersSet)

        val initialSelectAllState =
            filters.getBoolean(PrefsSettingsRepository.PREF_SELECT_ALL_KEY, true)
        selectAllSettings = MutableLiveData(initialSelectAllState)
    }

    override fun checkBoxSave(checkBox: CheckBoxSettings) {
        filtersSetValue = filtersSetValue.map {
            if (it.categoryId == checkBox.categoryId) checkBox else it
        }
        Log.d("TAG", "repository fun checkBoxSave $filtersSetValue")
    }

    override fun checkBoxesSelectAll() {
        filtersSetValue = filtersSetValue.map {
            if (it.isChecked) it else {
                it.copy(
                    isChecked = true
                )
            }
        }
        selectAllSettingsValue = true
        //extractCategoryIndexes(filtersSetValue)
        //val selectedСategories = filtersSetValue.filter { it.isChecked }

        //categoryIndexesForDBRequest = selectedСategories.map { it.categoryId } as Array<Int>
        Log.d("TAG", "repository checkBoxesSelectAll $filtersSetValue")
    }

    override fun checkBoxSelected(checkBox: CheckBoxSettings) {
        filtersSetValue = filtersSetValue.map {
            if (it.categoryId != checkBox.categoryId) it
            else {
                it.copy(
                    isChecked = !it.isChecked,
                )
            }
        }
    }

    override fun selectAllStateSave(state: Boolean) {
        selectAllSettingsValue = state
        Log.d("TAG", "repository checkBoxesSelectAll new value $selectAllSettingsValue")
    }

    fun extractCategoryIndexes (checkBoxList: List<CheckBoxSettings>) {
        var categoryIndexes = emptyList<Int>()
        for (checkBox in checkBoxList){
            if (checkBox.isChecked) categoryIndexes += checkBox.categoryId
        }
        //val prefIndexes: IntArray = categoryIndexes as IntArray
        filters.edit {
            val serializedIndexes = Json.encodeToString(categoryIndexes)
            putString(PrefsSettingsRepository.PREF_DB_INDEXES, serializedIndexes)
        }

    }

//    companion object {
//        var categoryIndexesForDBRequest = emptyArray<Int>()
//    }
}
//region externalFunctions


//endregion