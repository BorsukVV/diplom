package ru.netology.neRecipes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "authorName")
    val authorName: String,
    @ColumnInfo(name = "categorySpinnerPosition")
    val categorySpinnerPosition: Int,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "hasCustomImage")
    val hasCustomImage: Boolean,
    @ColumnInfo(name = "imageURI")
    val recipeImageUri: String?,
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean
)