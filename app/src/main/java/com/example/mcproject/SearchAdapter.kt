package com.example.mcproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcproject.databinding.SearchRvBinding

class SearchAdapter(
    private var dataList: ArrayList<Recipe>, // Changed to 'var' to allow reassignment
    private val context: Context
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    // ViewHolder using SearchRvBinding
    inner class ViewHolder(val binding: SearchRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>) {
        dataList = filterList // Reassigning dataList
        notifyDataSetChanged() // Notify RecyclerView of data changes
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        // Load image using Glide
        Glide.with(context)
            .load(item.img)
            .into(holder.binding.ima)

        // Set text
        holder.binding.textView6.text = item.tittle
        holder.itemView.setOnClickListener{
            var intent=Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing)
            context.startActivity(intent)
        }
    }
}
