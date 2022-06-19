package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private val someData = Data()
    override val data = MutableLiveData(
        List(1000) { index ->
            Post(
                id = index + 1L,
                authorName = "Netology",
                date = "13/06/2022",
                text = someData.getRandomContent(),
                isLiked = false,
                likesCount = 999,
                isReposted = false,
                repostsCount = 9995,
                viewesCount = 1299999,
            )
        }
    )

    private val posts
        get() = checkNotNull(data.value) {
            "Error. Data is null"
        }

    override fun like(postID: Long) {
        data.value = posts.map {
            if (it.id != postID) it
            else {
                it.copy(
                    isLiked = !it.isLiked,
                    likesCount = if (!it.isLiked) it.likesCount + 1 else it.likesCount - 1,
                )
            }
        }
    }

    override fun share(postID: Long) {

        data.value = posts.map {
            print("0")
            if (it.id != postID) it
            else {
                it.copy(
                    repostsCount = it.repostsCount + 1
                )
            }
        }
    }

}