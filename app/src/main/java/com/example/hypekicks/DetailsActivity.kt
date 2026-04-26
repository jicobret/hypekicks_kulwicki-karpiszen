package com.example.hypekicks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sneaker = intent.getSerializableExtra("sneaker") as Sneaker

        binding.name.text = "${sneaker.brand} ${sneaker.modelName}"
        binding.price.text = "$${sneaker.resellPrice}"

        Glide.with(this)
            .load(sneaker.imageUrl)
            .into(binding.image)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}