package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao

class SQLiteRepository(
    private val postDao: PostDao
) : PostRepository {

    private var posts = emptyList<Post>()
    override val data = MutableLiveData(postDao.getAll())

    init {
        posts = postDao.getAll()
        data.value = posts
    }

    override fun save(post: Post) {
        postDao.save(post)
        data.value = postDao.getAll()
    }

    override fun like(postID: Long) {
        postDao.likeByID(postID)
        data.value = postDao.getAll()
    }

    override fun delete(postID: Long) {
        postDao.removeByID(postID)
        data.value = postDao.getAll()
    }

    override var contentGeneratorButtonWasClicked: Boolean = true

    override fun share(postID: Long) {
        postDao.shareByID(postID)
        data.value = postDao.getAll()
    }

    override fun generateContent() {}
}