package com.example.mcproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mcproject.databinding.ActivityHomeBinding
import com.example.mcproject.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var recipes: List<Recipe> // Ensure non-nullable recipes
    private lateinit var dataList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextText.requestFocus()

        // Initialize Room database
        val db = Room.databaseBuilder(
            this, AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries() // Only for testing
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db") // Ensure this file exists in `assets`
            .build()

        val daoObj = db.getDao()
        recipes = daoObj.getAllRecipes()!!.filterNotNull() // Ensure no null values

        setUpRecyclerView()
        binding.goback.setOnClickListener {
            finish()
        }

        // Add TextWatcher for filtering
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    filterData(s.toString())
                } else {
                    rvAdapter.filterList(ArrayList(recipes)) // Reset to all recipes
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterData(filterText: String) {
        // Filter recipes based on title
        val filteredData = recipes.filter {
            it.tittle.contains(filterText, ignoreCase = true)
        }
        rvAdapter.filterList(ArrayList(filteredData))
    }

    private fun setUpRecyclerView() {
        // Initialize RecyclerView
        dataList = ArrayList(recipes) // Start with all recipes
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        rvAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = rvAdapter
    }
}