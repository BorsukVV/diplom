package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.MainActivityBinding

class MainActivity : AppCompatActivity(){

    //private val args by navArgs<PostContentFragmentArgs>()

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val intent = intent ?: return
    if (intent.action != Intent.ACTION_SEND) return
    val text = intent.getStringExtra(Intent.EXTRA_TEXT)
    if (text.isNullOrBlank()) return
    //args.initialContent
    val action = FeedFragmentDirections.toPostContentFragment(text)
    findNavController(R.id.nav_host_fragment).navigate(action)
}
}