package ru.netology.neRecipes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.neRecipes.data.RecipeRepository

@Dao
interface StepDao {

    @Query("SELECT * FROM steps ORDER BY id DESC")
    fun getAll(): LiveData<List<StepEntity>>

    @Insert
    fun insert(step: StepEntity)

    @Query("UPDATE steps SET stepDescription = :updatedDescription WHERE id = :id")
    fun updateDescriptionById(id: Int, updatedDescription: String)

    fun save(step: StepEntity) =
        if (step.id == RecipeRepository.NEW_STEP_ID) insert(step) else updateDescriptionById(step.id, step.stepDescription)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

}