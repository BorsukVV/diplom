package ru.netology.neRecipes.ui
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.PopupMenu
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.setFragmentResultListener
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import ru.netology.neRecipes.R
//import ru.netology.neRecipes.data.Recipe
//import ru.netology.neRecipes.databinding.PostDetailsFragmentBinding
//import ru.netology.neRecipes.ui.FeedFragmentDirections.Companion.toRecipeContentFragment
//import ru.netology.neRecipes.util.ViewsUtils
//import ru.netology.neRecipes.viewModel.RecipeViewModel
//
//class RecipeByStepsFragment : Fragment() {
//
//    private val viewModel by viewModels<RecipeViewModel>(
//        ownerProducer = ::requireParentFragment
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = PostDetailsFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
//
//        val post = viewModel.currentRecipe.value
//
//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//            val sortedPosts = posts.filter { it.id == post?.id }
//            if (sortedPosts.isNotEmpty()) {
//                binding.render(sortedPosts.first())
//            } else {
//                findNavController().navigateUp()
//            }
//        }
//
//        setFragmentResultListener(
//            requestKey = RecipeCreationFragment.REQUEST_KEY
//        ) { requestKey, bundle ->
//            if (requestKey != RecipeCreationFragment.REQUEST_KEY) return@setFragmentResultListener
//            val newPostContent = bundle.getString(
//                RecipeCreationFragment.RESULT_KEY
//            ) ?: return@setFragmentResultListener
//            viewModel.onSaveButtonClicked(newPostContent)
//        }
//
//        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, postContent)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)
//        }
//
//
//
//        post?.let {
//            binding.likesIcon.setOnClickListener {
//                viewModel.onLikeClicked(post)
//            }
//
//            binding.repostIcon.setOnClickListener {
//                viewModel.onShareClicked(post)
//            }
//
//            binding.videoFrameInPost.videoPoster.setOnClickListener {
//                viewModel.onVideoClicked(post)
//            }
//
//            val popupMenu by lazy {
//                PopupMenu(layoutInflater.context, binding.options).apply {
//                    inflate(R.menu.options_recipe)
//                    setOnMenuItemClickListener { menuItem ->
//                        when (menuItem.itemId) {
//                            R.id.remove -> {
//                                viewModel.onRemoveClicked(post)
//                                true
//                            }
//                            R.id.edit -> {
//                                viewModel.onEditClicked(post)
//                                true
//                            }
//                            else -> false
//                        }
//                    }
//                }
//            }
//
//            binding.options.setOnClickListener { popupMenu.show() }
//        }
//
//        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
//            val direction = RecipeByStepsFragment.toRecipeContentFragment(initialContent)
//            findNavController().navigate(direction)
//        }
//
//    }.root
//
//    private fun PostDetailsFragmentBinding.render(recipe: Recipe) {
//
//        recipe.let {
////            avatar.setImageResource(R.drawable.ic_launcher_foreground)
////            authorName.text = recipe.authorName
////            postText.text = recipe.text
////            data.text = recipe.date
////            likesIcon.text = ViewsUtils.countFormatter(resources, recipe.likesCount)
////            likesIcon.isChecked = recipe.isFavourite
////            repostIcon.text = ViewsUtils.countFormatter(resources, recipe.repostsCount)
////            viewsIcon.text = ViewsUtils.countFormatter(resources, recipe.viewsCount)
////            if (recipe.videoUrl != null) {
////                videoFrameInPost.root.visibility = View.VISIBLE
////                videoFrameInPost.videoUrl.text = recipe.videoUrl
////            } else {
////                videoFrameInPost.root.visibility = View.GONE
////            }
//        }
//    }
//}
