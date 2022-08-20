package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PrefDao {

    @Query("SELECT * FROM prefs WHERE contentGeneratorButtonWasClicked = contentGeneratorButtonWasClicked")
    fun checkWasClicked(): LiveData<List<PrefEntity>>

    @Query("UPDATE prefs SET contentGeneratorButtonWasClicked = 1")
    fun wasGenerated()

}