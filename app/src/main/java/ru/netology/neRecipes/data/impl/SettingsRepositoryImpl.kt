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

class SettingsRepositoryImpl private constructor(
    application: Application
) : PrefsSettingsRepository {

    //get preferences
    private val filters = application.getSharedPreferences(
        PrefsSettingsRepository.CATEGORY_FILTER, Context.MODE_PRIVATE
    )

    //private val checkBoxesNames: Array<String>

    override val filtersSet: MutableLiveData<List<CheckBoxSettings>>

    override val selectAllSettings: MutableLiveData<Boolean>

    val categoryIndexesForDBRequest: MutableLiveData<List<Int>>

    private var categoryIndexes
        get() = checkNotNull(categoryIndexesForDBRequest.value) {
            "Error. categoryIndexes is null"
        }
        set(value) {
            filters.edit {
                val serializedCategories = Json.encodeToString(value)
                putString(PrefsSettingsRepository.PREF_DB_INDEXES, serializedCategories)
            }
            categoryIndexesForDBRequest.value = value
        }

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
        //setup filters
        //get checkboxes names array
        val checkBoxesNames = application.resources.getStringArray(R.array.category_list)
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

        val serializedIndexes = filters.getString(PrefsSettingsRepository.PREF_DB_INDEXES, null)
        val categoryIndexes: List<Int> = if (serializedIndexes != null) {
            Json.decodeFromString(serializedIndexes)
        } else List(categoriesCount) { index ->
                index
            }

        categoryIndexesForDBRequest = MutableLiveData(categoryIndexes)

        val initialSelectAllState =
            filters.getBoolean(PrefsSettingsRepository.PREF_SELECT_ALL_KEY, true)
        selectAllSettings = MutableLiveData(initialSelectAllState)
    }

    override fun checkBoxSave(checkBox: CheckBoxSettings) {
        //copy to RAM and set changes to filterSet
        val modifiedSet = filtersSetValue.map {
            if (it.categoryId == checkBox.categoryId) checkBox else it
        }

        //Get list of checked checkBoxes
        val listChecked = modifiedSet.filter { it.isChecked }

        //Setup selectAll value
        selectAllSettingsValue = modifiedSet.size == listChecked.size

        //save changes in sharedPreferences
        filtersSetValue = if (listChecked.size == 1) {
            val singleCheckBox = listChecked.first()
            val disabledCheckBox = singleCheckBox.copy(isEnabled = false)
            modifiedSet.map {
                if (it.categoryId == disabledCheckBox.categoryId) disabledCheckBox else it
            }
        } else modifiedSet.map {
            if (it.isEnabled) it else it.copy(isEnabled = true)
        }

        extractCategoryIndexes()

    }

    override fun checkBoxesSelectAll() {
        filtersSetValue = filtersSetValue.map {
            it.copy(
                isChecked = true,
                isEnabled = true
            )
        }
        selectAllSettingsValue = true
        extractCategoryIndexes()
    }

    private fun extractCategoryIndexes() {
        val priorIndexes = filtersSetValue.map {
            if (it.isChecked) it.categoryId else NOT_CHECKED
        }
        categoryIndexes = priorIndexes.filterNot { it == NOT_CHECKED }
        //val prefIndexes: IntArray = categoryIndexes as IntArray
//        filters.edit {
//            val serializedIndexes = Json.encodeToString(categoryIndexes)
//            putString(PrefsSettingsRepository.PREF_DB_INDEXES, serializedIndexes)
//        }
        Log.d("TAG", "repository categoryIndexesForDBRequest.value ${categoryIndexesForDBRequest.value}")

    }

    companion object {
        private const val NOT_CHECKED = -1

        @Volatile
        private var instance: SettingsRepositoryImpl? = null

        fun getInstance(context: Application): SettingsRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: SettingsRepositoryImpl(context)
            }
        }
        //var categoryIndexesForDBRequest = emptyList<Int>()
    }
}
//region externalFunctions


//endregion