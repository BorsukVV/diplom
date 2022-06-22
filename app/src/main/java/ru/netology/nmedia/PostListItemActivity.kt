package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
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

        binding.saveButton.setOnClickListener {

            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
            }
        }

        binding.includeEditField.postEditCancelIcon.setOnClickListener {
            viewModel.onCancelClicked(
                binding.includeEditField.content.toString()
            )
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.text
                setText(content)
                binding.includeEditField.content.text = content

                if (content != null) {
                    requestFocus()
                    setSelection(content.length)
                    showKeyboard()
                    binding.includeEditField.postEditGroup.visibility = View.VISIBLE
                } else {
                    clearFocus()
                    hideKeyboard()
                    binding.includeEditField.postEditGroup.visibility = View.GONE
                }
            }

        }
    }
}