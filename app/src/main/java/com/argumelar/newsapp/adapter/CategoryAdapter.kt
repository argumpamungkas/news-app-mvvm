package com.argumelar.newsapp.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.ListCategoryBinding
import com.argumelar.newsapp.network.model.CategoryResponse
import com.argumelar.newsapp.ui.news.home.HomeViewModel

class CategoryAdapter(
    private val listCategory: ArrayList<CategoryResponse>,
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()

    class ViewHolder(val binding: ListCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listCategory[position]
        holder.binding.tvName.text = category.title

        items.add(holder.binding.tvName)
        holder.itemView.setOnClickListener {
            if (!it.isSelected){
                it.isSelected = true
                viewModel.selectCategory(category.id)
            } else {
                it.isSelected = false
                viewModel.selectCategory("")
            }
            setColor(holder.binding.tvName)
        }
    }

    override fun getItemCount() = listCategory.size

    fun setData(newCategory: ArrayList<CategoryResponse>) {
        listCategory.clear()
        listCategory.addAll(newCategory)
        notifyDataSetChanged()
    }

    private fun setColor(textView: TextView) {
        items.forEach {
            it.setTextColor(ContextCompat.getColor(it.context, R.color.grey))
            it.setTypeface(null, Typeface.DEFAULT.style)
        }
        if (textView.isSelected){
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.black))
            textView.setTypeface(null, Typeface.BOLD)
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.grey))
            textView.setTypeface(null, Typeface.DEFAULT.style)
        }
    }
}