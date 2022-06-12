package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository: PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 0,
            authorName = "Netology",
            date = "09/06/2022",
            text = "Чапаев приказал Петьке сварить зайца.\n" +
                    "- Где я его возьму, Василий Иванович? Здесь зайцы не водятся.\n" +
                    "- Это приказ, Петька! Я часок вздремну, а когда встану, чтоб была\n" +
                    "зайчина.\n" +
                    "Просыпается Чапаев и чувствует мясной запах. Видит Петька у котла\n" +
                    "шурует.\n" +
                    "- Петька, ты молодец! Расскажи, где ты его достал?\n" +
                    "- Взял я винтовку, вышел на крыльцо, бежит заяц. Я в него бабах! Он и\n" +
                    "мяукнуть не успел.",
            isLiked = false,
            likesCount = 999,
            isReposted = false,
            repostsCount = 9995,
            viewesCount = 1299999
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Error. Data is null"
        }
        val likedPost = currentPost.copy(
            isLiked = !currentPost.isLiked
        )
        data.value = likedPost
        setLikeCount(likedPost)
    }

    private fun setLikeCount(post: Post) {
        val updatedPost: Post = if (post.isLiked) {
            post.copy(likesCount = post.likesCount + 1)
        } else {
            post.copy(likesCount = post.likesCount - 1)
        }
        data.value = updatedPost
    }

    override fun share() {

        val currentPost = checkNotNull(data.value) {
            "Error. Data is null"
        }
        val sharedPost = currentPost.copy(
            repostsCount = currentPost.repostsCount + 1
        )
        data.value = sharedPost
    }

}