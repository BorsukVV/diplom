package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.PostListItemBinding

open class PostListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list_item)
        val binding = PostListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0,
            authorName = "Netology",
            date = "09/06/2022",
            text = "Greeting",
            isLiked = false,
            likesCount = 999,
            isReposted = false,
            repostsCount = 9995,
            viewesCount = 1299999
        )

        binding.render(post)

        binding.likesIcon.setOnClickListener {
            post.isLiked = !post.isLiked
            binding.likesIcon.setImageResource(getLikeIconResID(post.isLiked))
            setLikeCount(post)
            binding.render(post)
        }

        binding.repostIcon.setOnClickListener {
            setSharedCount(post)
            binding.repostCount.text = countFormatter(post.repostsCount)
            binding.render(post)
        }

//        binding.root.setOnClickListener { println("Сработало нажатие ROOT") }
//
//        binding.avatar.setOnClickListener { println("Сработало нажатие AVATAR") }

    }

    private fun PostListItemBinding.render(post: Post) {
        avatar.setImageResource(R.drawable.ic_launcher_foreground)
        authorName.text = post.authorName
        postText.text = post.text
        data.text = post.date
        likesIcon.setImageResource(getLikeIconResID(post.isLiked))
        likesCount.text = countFormatter(post.likesCount)
        repostCount.text = countFormatter(post.repostsCount)
        viewsCount.text = countFormatter(post.viewesCount)
    }

    @DrawableRes
    private fun getLikeIconResID(liked: Boolean): Int {
        return if (liked) R.drawable.ic_liked_icon_24dp else R.drawable.ic_likes_24dp
    }

    private fun setLikeCount(post: Post) {
        if (post.isLiked) post.likesCount++ else post.likesCount--
    }

    private fun setSharedCount(post: Post) {
        post.repostsCount++
    }

    //    private fun countFormatter(count: Int): String {
//        when (count) {
//            in (0 until 1000) -> {
//                return count.toString()
//            }
//            in (1000 until 10_000) -> {
//                val tensOfHundreds = count / 100
//                val hundreds = tensOfHundreds % 10
//                val thousands = tensOfHundreds / 10
//                return if (hundreds == 0) "${thousands}K" else "${thousands}.${hundreds}K"
//            }
//            in (10_000 until 1_000_000) -> {
//                val thousands = count / 1000
//                return "${thousands}K"
//            }
//            else -> {
//                val tensOfThousands = count / 100_000
//                val thousands = tensOfThousands % 10
//                val millions = tensOfThousands / 10
//                return if (thousands == 0) "${thousands}M" else "${millions}.${thousands}M"
//            }
//        }
//
//    }
    private fun countFormatter(count: Int): String {
        val template: String
        val suffix: String
        when (count) {
            in (0 until 1000) -> {
                template =
                    resources.getString(R.string.formatted_like_rep_view_count_without_suffix)
                return String.format(template, count)
            }
            in (1000 until 10_000) -> {
                val tensOfHundreds = count / 100
                val hundreds = tensOfHundreds % 10
                val thousands = tensOfHundreds / 10
                suffix = resources.getString(R.string.suffix_thousands)
                return stringOfTwoDigits(thousands, hundreds, suffix)
            }
            in (10_000 until 1_000_000) -> {
                val thousands = count / 1000
                suffix = resources.getString(R.string.suffix_thousands)
                template =
                    resources.getString(R.string.formatted_like_rep_view_thousands_one_dig_suf)
                return String.format(template, thousands, suffix)
            }
            else -> {
                val tensOfThousands = count / 100_000
                val thousands = tensOfThousands % 10
                val millions = tensOfThousands / 10
                suffix = resources.getString(R.string.suffix_millions)
                return stringOfTwoDigits(millions, thousands, suffix)
            }
        }

    }

    private fun stringOfTwoDigits(firstDigit: Int, secondDigit: Int, suffix: String): String {
        val template: String
        return if (secondDigit != 0) {
            template =
                resources.getString(R.string.formatted_like_rep_view_count_two_dig_suf)
            String.format(template, firstDigit, secondDigit, suffix)
        } else {
            template =
                resources.getString(R.string.formatted_like_rep_view_thousands_one_dig_suf)
            String.format(template, firstDigit, suffix)
        }
    }
}