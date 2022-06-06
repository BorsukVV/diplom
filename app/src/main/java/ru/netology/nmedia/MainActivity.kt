package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondActivityBtn: Button = findViewById (R.id.openThePost)

        secondActivityBtn.setOnClickListener() {
            val intent = Intent(this, PostListItemActivity::class.java)
            startActivity(intent)
        }
    }
}


