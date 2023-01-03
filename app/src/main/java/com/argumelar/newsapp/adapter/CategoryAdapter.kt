package com.argumelar.newsapp.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.ListCategoryBinding
import com.argumelar.newsapp.network.model.CategoryModel

class CategoryAdapter(
    private val listCategory: List<CategoryModel>,
    private val listener: OnAdapterListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

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
        holder.binding.tvName.text = category.name

        items.add(holder.binding.tvName)
        holder.itemView.setOnClickListener {
            listener.onClick(category)
            setColor(holder.binding.tvName)
        }
        setColor(items[0])
    }

    override fun getItemCount() = listCategory.size

    interface OnAdapterListener {
        fun onClick(category: CategoryModel)
    }

     fun setColor(textView: TextView) {
        items.forEach {
            it.setTextColor(ContextCompat.getColor(it.context, R.color.grey))
            it.setTypeface(null, Typeface.DEFAULT.style)
        }
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.black))
        textView.setTypeface(null, Typeface.BOLD)
    }
}