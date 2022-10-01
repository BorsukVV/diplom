package ru.netology.neRecipes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "steps",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
class StepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stepId")
    val id: Long,
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