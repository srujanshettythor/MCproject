package com.example.mcproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mcproject.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private var imgcrop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use view binding to set the content view
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.items)
        binding.title.text=intent.getStringExtra("tittle")
//        binding.ingtext.text=intent.getStringExtra("ing")
        binding.steptext.text=intent.getStringExtra("des")
        var ing=intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        binding.time.text=ing?.get(0)
        binding.ingtext.text = "" // Clear any existing text before appending
        for (i in 1 until ing!!.size) {
            binding.ingtext.append("🟢 ${ing[i]}\n")
        }
        binding.step.background=null
        binding.step.setTextColor(getColor(R.color.black))
        binding.step.setOnClickListener{
            binding.step.setBackgroundResource(R.drawable.btn_ing)
            binding.step.setTextColor(getColor(R.color.white))
            binding.button.setTextColor(getColor(R.color.black))
            binding.button.background=null
            binding.stepScroll.visibility=View.VISIBLE
            binding.ingScroll.visibility=View.GONE
        }
        binding.button.setOnClickListener{
            binding.button.setBackgroundResource(R.drawable.btn_ing)
            binding.button.setTextColor(getColor(R.color.white))
            binding.step.setTextColor(getColor(R.color.black))
            binding.step.background=null
            binding.ingScroll.visibility=View.VISIBLE
            binding.stepScroll.visibility=View.GONE

        }

        // Set click listener for full ImageView
        binding.full.setOnClickListener {
            if (imgcrop) {
                binding.items.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.items)
                binding.shade.visibility=View.GONE
            } else {
                binding.items.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.items)
                binding.shade.visibility=View.GONE
            }
            imgcrop = !imgcrop // Toggle the flag
        }
        binding.back.setOnClickListener {
            finish()
        }
    }
}