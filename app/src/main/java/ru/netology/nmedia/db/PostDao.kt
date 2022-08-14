package ru.netology.nmedia.db

import ru.netology.nmedia.data.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeByID(id:Long)
    fun removeByID(id: Long)
    fun shareByID(id: Long)

}