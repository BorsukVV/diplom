package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MainActivityBinding

class MainActivity : AppCompatActivity(){
    private val args by navArgs<PostContentFragmentArgs>()
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val intent = intent ?: return
    if (intent.action != Intent.ACTION_SEND) return
    val text = intent.getStringExtra(Intent.EXTRA_TEXT)
    if (text.isNullOrBlank()) return
    //args.initialContent.apply { PostContentFragmentArgs(text) }
    //println(args.initialContent)
//    findNavController(R.id.nav_host_fragment).navigate(
//        R.id.toPostContentFragment,
//        PostContentFragmentArgs(text).toBundle()
//    )
//    args.initialContent { PostContentFragmentArgs(text).toBundle()}
    //PostContentFragmentArgs(text).toBundle()
    supportFragmentManager.commit {
        val fragment = PostContentFragment()
        fragment.arguments = PostContentFragmentArgs(text).toBundle()
        add(R.id.nav_host_fragment, FeedFragment())
        addToBackStack(null)
        replace(R.id.nav_host_fragment, fragment)
        addToBackStack(null)
    }
}
}