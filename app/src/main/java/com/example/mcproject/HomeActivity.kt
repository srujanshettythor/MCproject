package com.example.mcproject

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mcproject.databinding.ActivityHomeBinding

class HomeActivity : ComponentActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root) // Use binding.root here

        setUpRecyclerView()
        binding.search.setOnClickListener(){
            startActivity(Intent(this,SearchActivity::class.java))
        }
        binding.salad.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.salad1.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }
        binding.salad2.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }
        binding.salad3.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.rec.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        // Room database setup
        val db = Room.databaseBuilder(
            this@HomeActivity, AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries() // Use for testing only
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()


        val daoObj = db.getDao()
        val recipes = daoObj.getAllRecipes()

        // Filter and add "Popular" recipes to dataList
        recipes?.forEach { recipe ->
            if (recipe?.category?.contains("Popular") == true) {
                dataList.add(recipe)
            }
        }

        // Initialize adapter and set it
        rvAdapter = PopularAdapter(dataList, this)
        binding.rec.adapter = rvAdapter
    }
}