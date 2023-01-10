package com.argumelar.newsapp.adapter

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argumelar.newsapp.R
import com.argumelar.newsapp.databinding.ListCategoryBinding
import com.argumelar.newsapp.network.model.CategoryResponse

class CategoryAdapter(
    private val listCategory: ArrayList<CategoryResponse>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()
    var currentCategoryId: String? = ""
    var currentCategoryTitle: String? = ""

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
            if (currentCategoryId != category.id || currentCategoryId == null) {
                listener.onClick(category)
                currentCategoryId = category.id
                currentCategoryTitle = category.title
            } else {
                listener.onClick(null)
                currentCategoryId = null
                currentCategoryTitle = ""
            }
            setColor()
        }
    }

    override fun getItemCount() = listCategory.size

    fun setData(newCategory: ArrayList<CategoryResponse>) {
        listCategory.clear()
        listCategory.addAll(newCategory)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(category: CategoryResponse?)
    }

    private fun setColor() {
        items.forEach {
            if (it.text == currentCategoryTitle) {
                it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                it.setTypeface(null, Typeface.BOLD)
            } else {
                it.setTextColor(ContextCompat.getColor(it.context, R.color.grey))
                it.setTypeface(null, Typeface.DEFAULT.style)
            }
        }
    }
}