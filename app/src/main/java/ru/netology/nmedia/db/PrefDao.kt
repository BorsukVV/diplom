package ru.netology.nmedia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PrefDao {

    @Insert
    fun insert(generatorPreset: PrefEntity)

    @Query("SELECT * FROM prefs WHERE id = 1")
    fun checkWasClicked(): List<PrefEntity>

    @Query("UPDATE prefs SET contentGeneratorButtonWasClicked = 1 WHERE id = 1")
    fun wasGenerated()

}