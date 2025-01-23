package com.example.mcproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mcproject.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var rvAdapter: CategoryAdapater
    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val titleText = intent.getStringExtra("TITLE")
        binding.title.text = titleText ?: "Default Title"
        setContentView(binding.root)
        title=intent.getStringExtra("TITLE")
        setUpRecyclerView()
        binding.goBack.setOnClickListener{
            finish()
        }
    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()

        binding.setrec.layoutManager = LinearLayoutManager(
            this
        )

        // Room database setup
        val db = Room.databaseBuilder(
            this@CategoryActivity, AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries() // Use for testing only
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db") // Ensure this asset exists in `assets` folder
            .build()

        val daoObj = db.getDao()
        val recipes = daoObj.getAllRecipes()

        // Filter and add "Popular" recipes to dataList
        recipes?.forEach { recipe ->
            if (recipe?.category?.contains(intent.getStringExtra("CATEGORY")!!) == true) {
                dataList.add(recipe)
            }
        }

        // Initialize adapter and set it
        rvAdapter = CategoryAdapater(dataList, this)
        binding.setrec.adapter = rvAdapter
    }
}