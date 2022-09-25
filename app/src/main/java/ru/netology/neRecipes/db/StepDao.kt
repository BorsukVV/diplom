package ru.netology.neRecipes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.neRecipes.data.RecipeRepository

@Dao
interface StepDao {

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId ORDER BY recipeId ASC ")
    fun getRequiredRangeOfSteps(
        recipeId: Long
    ): LiveData<List<StepEntity>>

    @Query("SELECT * FROM steps ORDER BY recipeId ASC ")
    fun getAllSteps(): LiveData<List<StepEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(step: StepEntity)

    @Transaction
    @Query(
        "UPDATE steps SET " +
                "recipeId = :recipeId, " +
                "sequentialNumber = :sequentialNumber, " +
                "stepDescription = :stepDescription, " +
                "hasCustomImage = :hasCustomImage, " +
                "imageUri = :stepImageUri WHERE stepId = :id"
    )
    fun updateDescriptionById(
        id: Long,
        recipeId: Long,
        sequentialNumber: Int,
        stepDescription: String,
        hasCustomImage: Boolean,
        stepImageUri: String?,
    )

    fun save(step: StepEntity) =
        if (step.id == RecipeRepository.NEW_STEP_ID) insert(step)
        else updateDescriptionById(
            step.id,
            step.recipeId,
            step.sequentialNumber,
            step.stepDescription,
            step.hasCustomImage,
            step.stepImageUri
        )

    @Transaction
    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

}