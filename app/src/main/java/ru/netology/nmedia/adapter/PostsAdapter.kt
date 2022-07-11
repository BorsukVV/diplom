package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding



internal class PostsAdapter(

    private val interActionListener: PostInterActionListener

) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInterActionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)

                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likesIcon.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.repostIcon.setOnClickListener {
                listener.onShareClicked(post)
            }

            binding.videoFrameInPost.videoPoster.setOnClickListener {
                listener.onVideoClicked(post)
            }

            binding.options.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)
                authorName.text = post.authorName
                postText.text = post.text
                data.text = post.date
                likesIcon.text = countFormatter(post.likesCount)
                likesIcon.isChecked = post.isLiked
                repostIcon.text = countFormatter(post.repostsCount)
                viewsIcon.text = countFormatter(post.viewesCount)
                if (post.videoUrl != null) {
                    postVideoGroup.visibility = View.VISIBLE
                    binding.videoFrameInPost.videoUrl.setText(post.videoUrl)
                } else {
                    postVideoGroup.visibility = View.GONE
                }
            }
        }

        private fun countFormatter(count: Int): String {

            val templateNoSuf =
                binding.root.context.resources.getString(R.string.formatted_like_rep_view_count_without_suffix)
            val suffixThousands =
                binding.root.context.resources.getString(R.string.suffix_thousands)
            val suffixMillions =
                binding.root.context.resources.getString(R.string.suffix_millions)

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
                    val hundreds = (count % 1000) / 100
                    return stringOfTwoDigits(thousands, hundreds, suffixThousands)
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

            val templateTwoDigSuf =
                binding.root.context.resources.getString(R.string.formatted_like_rep_view_count_two_dig_suf)
            val templateOneDigSuf =
                binding.root.context.resources.getString(R.string.formatted_like_rep_view_thousands_one_dig_suf)

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
        return ViewHolder(binding, interActionListener)
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

}