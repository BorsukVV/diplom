package ru.netology.nmedia.adapter


import ru.netology.nmedia.data.Post

interface PostInterActionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onVideoClicked(post: Post)
}