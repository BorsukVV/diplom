package ru.netology.neRecipes.adapter


import ru.netology.neRecipes.data.Step

interface StepInteractionListener {
    fun onRemoveClicked(step: Step)
    fun onEditClicked(step: Step)
    fun whenCreateOnRemoveClicked(step: Step)
    fun whenCreateOnEditClicked(step: Step)
}