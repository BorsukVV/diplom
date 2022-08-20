package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInterActionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInterActionListener {

    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao,
        pref = AppDb.getInstance(
            context = application
        ).prefDao
    )

    val data by repository::data

    var contentGeneratorButtonVisibility = !repository.contentGeneratorButtonWasClicked

    val sharePostContent = SingleLiveEvent<String>()

    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()

    val navigateToPostDetails = SingleLiveEvent<Long>()

    val currentPost = MutableLiveData<Post?>(null)

    val playVideoContent = SingleLiveEvent<String>()

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
            viewsCount = 0,
            videoUrl = null
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddClicked() {
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
        navigateToPostContentScreenEvent.value = post.text
    }

    override fun onVideoClicked(post: Post) {
        post.videoUrl?.let {
            playVideoContent.value = it
        }
    }

    override fun viewPostDetails(post: Post) {
        currentPost.value = post
        navigateToPostDetails.value = post.id
    }

    fun contentGeneratorClicked() = repository.generateContent()


    //endregion
}