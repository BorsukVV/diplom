package ru.netology.nmedia.adapter


import ru.netology.nmedia.Post

interface PostInterActionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
   // fun onCancelClicked(post: Post)
}