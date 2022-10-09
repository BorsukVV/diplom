package ru.netology.neRecipes.viewModel

import android.app.Application
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

    val selectAllState by repository::selectAllSettings

    override fun onItemClicked(checkBox: CheckBoxSettings) = repository.checkBoxSave(checkBox)

    fun selectAll() = repository.checkBoxesSelectAll()

}