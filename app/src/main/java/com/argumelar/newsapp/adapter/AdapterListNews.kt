package com.argumelar.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.databinding.AdapterNewsBinding
import com.argumelar.newsapp.network.model.DataNews
import com.argumelar.newsapp.util.DateFormat
import com.squareup.picasso.Picasso

class AdapterListNews(val newsLists: ArrayList<DataNews>, val listener: OnAdapterListener): RecyclerView.Adapter<AdapterListNews.ViewHolder>() {

    class ViewHolder(val binding: AdapterNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsLists[position]
        val date = DateFormat()
        val newDate = date.dateFormat(news.date!!)
        holder.binding.tvTitle.text = news.title
        holder.binding.tvDescription.text = news.description
        holder.binding.authorDate.text = news.author + " - " + newDate

        val image = news.image
        Picasso.get()
            .load(image)
            .into(holder.binding.ivImage)

        holder.itemView.setOnClickListener {
            listener.onClick(news)
        }
    }

    override fun getItemCount() = newsLists.size

    fun setData(news: List<DataNews>){
        newsLists.clear()
        newsLists.addAll(news)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(news: DataNews)
    }

}