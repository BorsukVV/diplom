package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.PostEntity
import ru.netology.nmedia.db.PrefDao

class PostRepositoryImpl(
    private val dao: PostDao,
    private val pref: PrefDao

) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) = dao.save(post.toEntity())

    override fun like(postID: Long) = dao.likeById(postID)

    override fun delete(postID: Long) = dao.removeById(postID)


    override var contentGeneratorButtonWasClicked =
        pref.checkWasClicked()

    private var generatorButtonInvisible: Boolean =
        contentGeneratorButtonWasClicked.value?.last()?.contentGeneratorButtonWasClicked == true

    override fun share(postID: Long) = dao.shareByID(postID)

    override fun generateContent() {
//        val before = pref.checkWasClicked().value?.last()?.contentGeneratorButtonWasClicked
//        Log.d("TAG", "fun generateContent checkWasClicked before if $before")
        if (!generatorButtonInvisible) {
            val someData = Data()
            for (index: Int in 0..someData.getContentCount()) {
                save(
                    Post(
                        id = PostRepository.NEW_POST_ID,
                        authorName = "Netology",
                        date = "20/08/2022",
                        text = "#${index + 1} \n" + someData.getRandomContent(),
                        isLiked = false,
                        likesCount = (0 until 2_300_000).random(),
                        isReposted = false,
                        repostsCount = (0 until 500_000).random(),
                        viewsCount = (0 until 5_300_000).random(),
                        videoUrl = someData.getRandomURL()
                    )
                )
            }
        }
        pref.wasGenerated()
//        val logMsg = pref.checkWasClicked().value?.last()?.contentGeneratorButtonWasClicked
//        Log.d("TAG", "fun generateContent checkWasClicked $logMsg")
    }
}

private fun Post.toEntity() = PostEntity(
    id = id,
    authorName = authorName,
    date = date,
    text = text,
    isLiked = isLiked,
    likesCount = likesCount,
    isReposted = isReposted,
    repostsCount = repostsCount,
    viewsCount = viewsCount,
    videoUrl = videoUrl
)

private fun PostEntity.toModel() = Post(
    id = id,
    authorName = authorName,
    date = date,
    text = text,
    isLiked = isLiked,
    likesCount = likesCount,
    isReposted = isReposted,
    repostsCount = repostsCount,
    viewsCount = viewsCount,
    videoUrl = videoUrl
)