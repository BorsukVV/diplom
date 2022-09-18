package ru.netology.neRecipes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
class StepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "recipeId")
    val recipeId: Long,
    @ColumnInfo(name = "sequentialNumber")
    val sequentialNumber: Int,
    @ColumnInfo(name = "stepDescription")
    val stepDescription: String,
    @ColumnInfo(name = "hasCustomImage")
    val hasCustomImage: Boolean,
    @ColumnInfo(name = "imageUri")
    val stepImageUri: String?,
)