package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.PostEntity
import ru.netology.nmedia.db.PrefDao
import ru.netology.nmedia.db.PrefEntity

class PostRepositoryImpl(
    private val dao: PostDao,
    private val pref: PrefDao

) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
        else dao.updateContentById(post.id, post.text)
    }

    override fun like(postID: Long) {
        dao.likeById(postID)
    }

    override fun delete(postID: Long) {
        dao.removeById(postID)
    }

//    override var contentGeneratorButtonWasClicked: Boolean = true
//   override var contentGeneratorButtonWasClicked: Boolean = prefChecker()

    override var contentGeneratorButtonWasClicked: Boolean = pref.checkWasClicked().last().contentGeneratorButtonWasClicked


private fun prefChecker() : Boolean {
    if (pref.checkWasClicked().isNullOrEmpty()){
        val generatorPreset = PrefEntity(1, false)
        pref.insert(generatorPreset)
    }
    return pref.checkWasClicked().last().contentGeneratorButtonWasClicked

}

    override   fun share(postID: Long) {
        dao.shareByID(postID)
    }

    override fun generateContent() {
        if (!contentGeneratorButtonWasClicked) {
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
    }

}

//private fun PrefEntity.toBoolean(): Boolean {
//
//}

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