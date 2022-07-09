package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInterActionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInterActionListener {
    private val repository: PostRepository = InMemoryPostRepository()
    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()

    val editPostContent = SingleLiveEvent<String>()

    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {

        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            text = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Netology",
            date = "19/06/2022",
            text = content,
            isLiked = false,
            likesCount = 0,
            isReposted = false,
            repostsCount = 0,
            viewesCount = 0,
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddClicked(post: Post?) {
        navigateToPostContentScreenEvent.call()
    }

    //region PostInterActionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.text
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {

        currentPost.value = post
        editPostContent.value = post.text

    }

    fun onCancelClicked(content: String) {
        currentPost.value = null
        return
    }


    //endregion
}