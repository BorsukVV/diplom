package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.data.Post

class PostDaoImpl(
    private val db: SQLiteDatabase
) : PostDao {

    override fun getAll() = db.query(
        PostTable.NAME,
        PostTable.ALL_COLUMNS_NAMES,
        null, null, null, null,
        "${PostTable.Column.Id.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostTable.Column.AuthorName.columnName, post.authorName)
            put(PostTable.Column.Date.columnName, post.date)
            put(PostTable.Column.Text.columnName, post.text)
            put(PostTable.Column.IsLiked.columnName, post.isLiked)
            put(PostTable.Column.LikesCount.columnName, post.likesCount)
            put(PostTable.Column.IsReposted.columnName, post.isReposted)
            put(PostTable.Column.ViewsCount.columnName, post.viewesCount)
            put(PostTable.Column.VideoUrl.columnName, post.videoUrl)
        }
        val id = if (post.id != 0L) {
            db.update(
                PostTable.NAME,
                values,
                "${PostTable.Column.Id.columnName} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {//post.id == 0L
            db.insert(PostTable.NAME, null, values)
        }
        return db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            "${PostTable.Column.Id.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun likeByID(id: Long) {
        db.execSQL(
            """UPDATE ${PostTable.NAME} SET
                    ${PostTable.Column.LikesCount} = ${PostTable.Column.LikesCount} + 
CASE WHEN ${PostTable.Column.IsLiked} THEN -1 ELSE 1 END,
                    ${PostTable.Column.IsLiked} = CASE WHEN ${PostTable.Column.IsLiked} THEN 0 ELSE 1 END
                WHERE ${PostTable.Column.Id.columnName} = ?;"""
                .trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeByID(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.Id.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun shareByID(id: Long) {
        db.execSQL(
            """UPDATE ${PostTable.NAME} SET
                ${PostTable.Column.RepostsCount} = ${PostTable.Column.RepostsCount} + 1
                WHERE ${PostTable.Column.Id.columnName} = ?;"""
                .trimIndent(),
            arrayOf(id)
        )
    }
}