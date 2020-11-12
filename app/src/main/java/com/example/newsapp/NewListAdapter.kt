package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener(){
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.authorView.text = currentItem.author
        holder.dateView.text = currentItem.publishedAt.substring(0,10)
        holder.descriptionView.text = currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.imageURL).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(updaNews: ArrayList<News>){
        items.clear()
        items.addAll(updaNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val authorView: TextView = itemView.findViewById(R.id.author)
    val dateView: TextView = itemView.findViewById(R.id.date)
    val descriptionView: TextView = itemView.findViewById(R.id.description)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
}

interface NewsItemClicked{
    fun onItemClicked(item: News)
}