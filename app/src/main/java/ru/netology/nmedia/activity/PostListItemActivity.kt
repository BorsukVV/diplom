package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.viewModel.PostViewModel

open class PostListItemActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list_item)
        val binding = PostListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.PostsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked(null)
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.editPostContent.observe(this) {
            val textForEdit = viewModel.currentPost.value?.text
            println(textForEdit)
            getDataFromPostContentActivity(textForEdit)

        }
        getDataFromPostContentActivity("don't worry")

    }

    fun getDataFromPostContentActivity(textForSend: String? = "Введите текст поста") {
        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch(textForSend)
        }
    }

}