package com.example.mcproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Intent

import com.example.mcproject.databinding.PopularItemBinding

class PopularAdapter(var dataList:ArrayList<Recipe>,var context:Context):RecyclerView.Adapter<PopularAdapter.viewHolder>() {
    inner class viewHolder(var binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding=PopularItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Glide.with(context).load(dataList.get(position).img).into(holder.binding.c1)
        holder.binding.pop1.text=dataList.get(position).tittle
        var time=dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.binding.poptime.text=time.get(0)
        holder.itemView.setOnClickListener{
            var intent=Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}