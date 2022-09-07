package ru.netology.neRecipes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.neRecipes.data.RecipeRepository

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Query(
        "UPDATE recipes SET title = :updatedTitle, " +
                "authorName = :updatedAuthor, " +
                "category = :updatedCategory, " +
                "description = :updatedDescription  WHERE id = :id"
    )
    fun updateDescriptionById(
        id: Long,
        updatedTitle: String,
        updatedAuthor: String,
        updatedCategory: String,
        updatedDescription: String
    )

    fun save(recipe: RecipeEntity) =
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) {
            insert(recipe)
        } else {
            updateDescriptionById(
                recipe.id,
                recipe.title,
                recipe.authorName,
                recipe.category,
                recipe.description
            )
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