package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.db.PrefEntity

interface PostRepository {
    val data: LiveData<List<Post>>
    var contentGeneratorButtonWasClicked: LiveData<List<PrefEntity>>
    fun like(postID: Long)
    fun share(postID: Long)
    fun delete(postID: Long)
    fun save(post: Post)
    fun generateContent()

    companion object {
        const val NEW_POST_ID = 0L
    }
}