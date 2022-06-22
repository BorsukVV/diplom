package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondActivityBtn: Button = findViewById(R.id.openThePost)

        secondActivityBtn.setOnClickListener() {
            val intent = Intent(this, PostListItemActivity::class.java)
            startActivity(intent)
        }
    }
}



