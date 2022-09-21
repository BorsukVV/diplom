package ru.netology.neRecipes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.neRecipes.data.RecipeRepository

@Dao
interface StepDao {

    @Query("SELECT * FROM steps WHERE recipeId = :rangeOfSteps ORDER BY id ASC ")
    fun getRequiredRangeOfSteps(
        rangeOfSteps: Long
    ): LiveData<List<StepEntity>>

    @Query("SELECT * FROM steps ORDER BY recipeId ASC ")
    fun getAllSteps(): LiveData<List<StepEntity>>

    @Insert
    fun insert(step: StepEntity)

    @Query("UPDATE steps SET " +
            "recipeId = :recipeId, " +
            "sequentialNumber = :sequentialNumber, " +
            "stepDescription = :stepDescription, " +
            "hasCustomImage = :hasCustomImage, " +
            "imageUri = :stepImageUri WHERE id = :id")
    fun updateDescriptionById(
        id: Int,
        recipeId: Long,
        sequentialNumber: Int,
        stepDescription: String,
        hasCustomImage:Boolean,
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

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Int)

}