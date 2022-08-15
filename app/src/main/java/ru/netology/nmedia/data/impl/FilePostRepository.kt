package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class FilePostRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextID: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREF_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREF_KEY, newValue) }
    }

    override var contentGeneratorButtonWasClicked =
        prefs.getBoolean(
            GENERATED_POSTS_PREF_KEY,
            false
        )

    override val data: MutableLiveData<List<Post>>


    private var posts
        get() = checkNotNull(data.value) {
            "Error. Data is null"
        }
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use { it.write(gson.toJson(value)) }
            data.value = value
        }

    init {
        val postFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use {
                gson.fromJson(it, type)
            }
        } else {
            emptyList()
        }
        data = MutableLiveData(posts)
    }

    override fun like(postID: Long) {
        posts = posts.map {
            if (it.id != postID) it
            else {
                it.copy(
                    isLiked = !it.isLiked,
                    likesCount = it.likesCount + if (!it.isLiked) +1 else -1,
                )
            }
        }
    }

    override fun share(postID: Long) {

        posts = posts.map {
//            print("0")
            if (it.id != postID) it
            else {
                it.copy(
                    repostsCount = it.repostsCount + 1
                )
            }
        }
    }

    override fun delete(postID: Long) {
        posts = posts.filterNot { it.id == postID }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun generateContent() {
        if (!contentGeneratorButtonWasClicked) {

            val someData = Data()
            val generatedContent = List(someData.getContentCount()) { index ->
                Post(
                    id = index + 1L,
                    authorName = "Netology",
                    date = "13/06/2022",
                    text = "#${index + 1} \n" + someData.getRandomContent(),
                    isLiked = false,
                    likesCount = (0 until 2_300_000).random(),
                    isReposted = false,
                    repostsCount = (0 until 500_000).random(),
                    viewsCount = (0 until 5_300_000).random(),
                    videoUrl = someData.getRandomURL()
                )
            }
            posts = generatedContent + posts
        }
        prefs.edit { putBoolean(GENERATED_POSTS_PREF_KEY, true) }
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(post.copy(id = ++nextID)) + posts
    }

    private companion object {
        const val GENERATED_POSTS_PREF_KEY = "Posts Generator Was Started"
        const val POSTS_PREF_KEY = "posts"
        const val NEXT_ID_PREF_KEY = "nextID"
        const val FILE_NAME = "posts.json"
    }

}