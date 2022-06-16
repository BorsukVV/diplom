package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

internal class PostsAdapter(
    private val onLikeClicked: OnLikeListener,
    private val onShareClicked: OnShareListener,
    val templateNoSuf: String,
    val templateOneDigSuf: String,
    val templateTwoDigSuf: String,
    val suffixThousands: String,
    val suffixMillions: String

) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    inner class ViewHolder(
        private val binding: PostBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likesIcon.setOnClickListener {
                onLikeClicked(post)
            }
            binding.repostIcon.setOnClickListener {
                onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)
                authorName.text = post.authorName
                postText.text = post.text
                data.text = post.date
                likesIcon.setImageResource(getLikeIconResID(post.isLiked))
                likesCount.text = countFormatter(post.likesCount)
                repostCount.text = countFormatter(post.repostsCount)
                viewsCount.text = countFormatter(post.viewesCount)
            }
        }

        @DrawableRes
        private fun getLikeIconResID(liked: Boolean): Int {
            return if (liked) R.drawable.ic_liked_icon_24dp else R.drawable.ic_likes_24dp
        }

        private fun countFormatter(count: Int): String {
            when (count) {
                in (0 until 1000) -> {
                    return String.format(templateNoSuf, count)
                }
                in (1000 until 10_000) -> {
                    val tensOfHundreds = count / 100
                    val hundreds = tensOfHundreds % 10
                    val thousands = tensOfHundreds / 10
                    return stringOfTwoDigits(thousands, hundreds, suffixThousands)
                }
                in (10_000 until 1_000_000) -> {
                    val thousands = count / 1000
                    return String.format(templateOneDigSuf, thousands, suffixThousands)
                }
                else -> {
                    val tensOfThousands = count / 100_000
                    val thousands = tensOfThousands % 10
                    val millions = tensOfThousands / 10
                    return stringOfTwoDigits(millions, thousands, suffixMillions)
                }
            }

        }

        private fun stringOfTwoDigits(firstDigit: Int, secondDigit: Int, suffix: String): String {
            return if (secondDigit != 0) {
                String.format(templateTwoDigSuf, firstDigit, secondDigit, suffix)
            } else {
                String.format(templateOneDigSuf, firstDigit, suffix)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }

    //override fun getItemCount() =

}