package ru.netology.neRecipes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.neRecipes.data.RecipeRepository

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes WHERE categorySpinnerPosition IN (:categoryIndexes) ORDER BY id DESC")
    fun getAll(categoryIndexes: Array<Int>): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeByID(id: Long): RecipeEntity

    @Query("SELECT * FROM recipes WHERE isFavourite = 1 ORDER BY id DESC")
    fun getAllFavorites(): LiveData<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: RecipeEntity): Long

    @Query(
        "UPDATE recipes SET title = :updatedTitle, " +
                "authorName = :updatedAuthor, " +
                "category = :updatedCategory, " +
                "description = :updatedDescription, " +
                "imageURI = :updatedImageURI  WHERE id = :id"
    )
    fun updateDescriptionById(
        id: Long,
        updatedTitle: String,
        updatedAuthor: String,
        updatedCategory: String,
        updatedDescription: String,
        updatedImageURI: String?
    )

    fun save(recipe: RecipeEntity): Long {
        return if (recipe.id == RecipeRepository.NEW_RECIPE_ID) {
            insert(recipe)
        } else {
            updateDescriptionById(
                recipe.id,
                recipe.title,
                recipe.authorName,
                recipe.category,
                recipe.description,
                recipe.recipeImageUri
            )
            recipe.id
        }
    }

    @Query(
        """
        UPDATE recipes SET isFavourite = CASE WHEN isFavourite THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun chooseFavoriteById(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

}