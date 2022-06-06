package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

open class PostListItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list_item)
    }
}