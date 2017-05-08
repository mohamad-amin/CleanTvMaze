package com.mohamadamin.cleantvmaze.page.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mohamadamin.cleantvmaze.R
import com.mohamadamin.cleantvmaze.domain.entity.Show

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/7/17.
 */
class MainAdapter(private val context: Context, var shows: List<Show> = emptyList()):
        RecyclerView.Adapter<MainViewHolder>() {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindImage(shows[position].image.medium)
        holder.bindName(shows[position].name)
    }

    override fun getItemCount(): Int = shows.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
            MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_show_item, parent, false))

}