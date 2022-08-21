package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prefs")
class PrefEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", defaultValue = "1")
    val id: Int,
    @ColumnInfo(name = "contentGeneratorButtonWasClicked", defaultValue = "0")
    val contentGeneratorButtonWasClicked: Boolean
)