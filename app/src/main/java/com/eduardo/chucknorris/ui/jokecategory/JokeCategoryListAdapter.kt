package com.eduardo.chucknorris.ui.jokecategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eduardo.chucknorris.R
import com.eduardo.chucknorris.model.JokeCategory
import kotlinx.android.synthetic.main.item_list_joke_category.view.*

class JokeCategoryListAdapter(private val ctx: Context, private val onClick:(JokeCategory)->Unit): RecyclerView.Adapter<JokeCategoryListAdapter.ViewHolder>() {

    var categoryList: List<JokeCategory> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.item_list_joke_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(jokeCategory: JokeCategory) {
            itemView.categoryName.text = jokeCategory.name
            itemView.setOnClickListener {
                onClick(jokeCategory)
            }
        }

    }
}