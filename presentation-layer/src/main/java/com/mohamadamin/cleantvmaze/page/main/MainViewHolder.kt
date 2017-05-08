package com.mohamadamin.cleantvmaze.page.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.mohamadamin.cleantvmaze.R

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/7/17.
 */
class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {

    @BindView(R.id.show_image)
    lateinit var image: ImageView
    @BindView(R.id.show_name)
    lateinit var name: TextView

    init {
        ButterKnife.bind(this, view)
    }

    fun bindImage(url: String) =
            Glide.with(itemView.context.applicationContext)
                    .load(url)
                    .crossFade()
                    .fitCenter()
                    .error(R.drawable.ic_signal_wifi_off_black_90dp)
                    .into(image)

    fun bindName(name: String) {
        this.name.text = name
    }

}