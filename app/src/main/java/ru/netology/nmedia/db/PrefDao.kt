package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(generatorPreset: PrefEntity)

    @Query("SELECT * FROM prefs WHERE id = 1")
    fun checkWasClicked(): LiveData<List<PrefEntity>>

    @Query("UPDATE prefs SET contentGeneratorButtonWasClicked = 1 WHERE id = 1")
    fun wasGenerated()

}