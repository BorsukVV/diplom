package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.PostDetailsFragmentBinding
import ru.netology.nmedia.util.ViewsUtils
import ru.netology.nmedia.viewModel.PostViewModel

class PostDetailsFragment: Fragment() {

    private val viewModel by viewModels<PostViewModel>(
    ownerProducer = ::requireParentFragment

)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostDetailsFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val post = viewModel.currentPost.value

        viewModel.data.observe(viewLifecycleOwner) {posts ->
            val sortedPosts = posts.filter{ it.id == post?.id }
            if (sortedPosts.isNotEmpty()) {
                binding.render(sortedPosts.first())
            } else {
                findNavController().navigateUp()
            }

        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideoContent.observe(viewLifecycleOwner) { postUrl ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postUrl)
            }
            startActivity(intent)
        }

        binding.likesIcon.setOnClickListener{
            if (post != null) {
                viewModel.onLikeClicked(post)
            }
        }

        binding.repostIcon.setOnClickListener {
            if (post != null) {
                viewModel.onShareClicked(post)
            }
        }

        binding.videoFrameInPost.videoPoster.setOnClickListener {
            if (post != null) {
                viewModel.onVideoClicked(post)
            }
        }

        val popupMenu by lazy {
            PopupMenu(layoutInflater.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            if (post != null) {
                                viewModel.onRemoveClicked(post)
                            }
                            true
                        }
                        R.id.edit -> {
                            if (post != null) {
                                viewModel.onEditClicked(post)
                            }

                            true
                        }
                        else -> false
                    }
                }
            }
        }

        binding.options.setOnClickListener { popupMenu.show() }

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
            val direction = PostDetailsFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }

    }.root

    private fun PostDetailsFragmentBinding.render(post: Post) {

        post.let {
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            authorName.text = post.authorName
            postText.text = post.text
            data.text = post.date
            likesIcon.text = ViewsUtils.countFormatter(resources, post.repostsCount)
            likesIcon.isChecked = post.isLiked
            repostIcon.text = ViewsUtils.countFormatter(resources, post.repostsCount)
            viewsIcon.text = ViewsUtils.countFormatter(resources, post.repostsCount)
            if (post.videoUrl != null) {
                postDetailsVideoGroup.visibility = View.VISIBLE
                videoFrameInPost.videoUrl.text = post.videoUrl
            } else {
                postDetailsVideoGroup.visibility = View.GONE
            }
        }

    }

}