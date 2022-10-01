package ru.netology.neRecipes.util

import kotlinx.serialization.Serializable

@Serializable
data class CheckBoxSettings (
    val categoryId: Int,
    val category: String,
    val isChecked: Boolean = true
        )