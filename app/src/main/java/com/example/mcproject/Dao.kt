package com.example.mcproject

import androidx.room.Dao
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes():List<Recipe?>?
}
