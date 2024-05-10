
package com.oguzhanozgokce.healthandprediction.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.data.model.modelNews.Result

class NewsListAdapter(
    private val results: List<Result>,
    private val onItemClickListener: (Result) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(results[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.news_imageView_ID)
        private val titleTextView: TextView = itemView.findViewById(R.id.news_title_text_view_Id)
        private val sourceTextView: TextView = itemView.findViewById(R.id.news_author_text_view_Id)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.news_description_text_view_Id)
        fun bind(result: Result, onItemClickListener: (Result) -> Unit) {

            titleTextView.text = result.name
            sourceTextView.text = result.source
            descriptionTextView.text = result.description
            Glide.with(itemView.context)
                .load(result.image)
                .into(imageView)

            itemView.setOnClickListener { onItemClickListener(result) }
        }
    }
}