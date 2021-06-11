package com.example.prism.dev.ashis.patel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PostDetailsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details_screen)

        val bundle : Bundle?=intent.extras
        val companyName = bundle?.getString("companyName")
        val companyImageUrl = bundle?.getString("companyImageUrl")
        val companyImage : ImageView = findViewById(R.id.companyImage)
        supportActionBar?.title =companyName
        Picasso.get().load(companyImageUrl).placeholder(R.drawable.loa).into(companyImage)


    }
}