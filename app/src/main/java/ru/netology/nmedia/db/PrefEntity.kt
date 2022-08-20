package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prefs")
class PrefEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "contentGeneratorButtonWasClicked", defaultValue = "0")
    val contentGeneratorButtonWasClicked: Boolean
)