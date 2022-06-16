package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.viewModel.PostViewModel

open class PostListItemActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list_item)
        val binding = PostListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val templateNoSuf = resources.getString(R.string.formatted_like_rep_view_count_without_suffix)
        val templateOneDigSuf = resources.getString(R.string.formatted_like_rep_view_thousands_one_dig_suf)
        val templateTwoDigSuf = resources.getString(R.string.formatted_like_rep_view_count_two_dig_suf)
        val suffixThousands = resources.getString(R.string.suffix_thousands)
        val suffixMillions = resources.getString(R.string.suffix_millions)

        val adapter = PostsAdapter(
            viewModel::onLikeClicked,
            viewModel::onShareClicked,
            templateNoSuf,
            templateOneDigSuf,
            templateTwoDigSuf,
            suffixThousands,
            suffixMillions
        )

        binding.PostsRecyclerView.adapter = adapter

        viewModel.data.observe(this) {
                posts -> adapter.submitList(posts)
        }
    }
}