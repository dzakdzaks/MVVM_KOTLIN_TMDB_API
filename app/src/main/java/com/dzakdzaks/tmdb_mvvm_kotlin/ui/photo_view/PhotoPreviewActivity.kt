package com.dzakdzaks.tmdb_mvvm_kotlin.ui.photo_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dzakdzaks.tmdb_mvvm_kotlin.R
import kotlinx.android.synthetic.main.activity_photo_preview.*

class PhotoPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)

        if (intent != null) {
            Glide.with(this)
                .load( intent.getStringExtra("url"))
                .into(photo_view)
        }

        imgClose.setOnClickListener {
            finish()
        }
    }
}
