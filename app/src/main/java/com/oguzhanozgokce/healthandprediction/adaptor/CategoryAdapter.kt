package com.oguzhanozgokce.healthandprediction.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.data.model.Category

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_category_icon)
        val textView: TextView = itemView.findViewById(R.id.textView_category_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_categori, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = categoryList.size
}