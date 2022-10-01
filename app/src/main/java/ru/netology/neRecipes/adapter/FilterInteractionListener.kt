package ru.netology.neRecipes.adapter

import ru.netology.neRecipes.util.CheckBoxSettings


interface FilterInteractionListener {
    //fun selectAll()
    fun onItemClicked(checkBox: CheckBoxSettings)
}