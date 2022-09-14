package ru.netology.neRecipes.adapter


import ru.netology.neRecipes.data.Step

interface StepInterActionListener {
    fun onRemoveClicked(step: Step)
    fun onEditClicked(step: Step)
    fun onImageClicked(step: Step)
}