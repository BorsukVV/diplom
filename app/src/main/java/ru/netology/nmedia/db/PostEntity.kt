package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "authorName")
    val authorName: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "isLiked")
    val isLiked: Boolean,
    @ColumnInfo(name = "likesCount")
    val likesCount: Int,
    @ColumnInfo(name = "isReposted")
    val isReposted: Boolean,
    @ColumnInfo(name = "repostsCount")
    val repostsCount: Int,
    @ColumnInfo(name = "viewsCount")
    val viewsCount: Int,
    @ColumnInfo(name = "videoUrl")
    val videoUrl: String?
)