package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.data.Post

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostTable.Column.Id.columnName)),
    authorName = getString(getColumnIndexOrThrow(PostTable.Column.AuthorName.columnName)),
    date = getString(getColumnIndexOrThrow(PostTable.Column.Date.columnName)),
    text = getString(getColumnIndexOrThrow(PostTable.Column.Text.columnName)),
    isLiked = getInt(getColumnIndexOrThrow(PostTable.Column.IsLiked.columnName)) != 0,
    likesCount = getInt(getColumnIndexOrThrow(PostTable.Column.LikesCount.columnName)),
    isReposted = getInt(getColumnIndexOrThrow(PostTable.Column.IsReposted.columnName)) != 0,
    repostsCount = getInt(getColumnIndexOrThrow(PostTable.Column.RepostsCount.columnName)),
    viewesCount = getInt(getColumnIndexOrThrow(PostTable.Column.ViewsCount.columnName)),
    videoUrl = getString(getColumnIndexOrThrow(PostTable.Column.VideoUrl.columnName))

)