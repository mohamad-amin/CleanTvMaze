package com.mohamadamin.cleantvmaze.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import com.mohamadamin.cleantvmaze.R

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/8/17.
 * A recycler view that automatically chooses span count to best fit the device's screen size.
 */
class AutofitRecyclerView: RecyclerView {

    private var manager: StaggeredGridLayoutManager? = null
    private var columnWidth: Int = 0

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutofitRecyclerView, 0, 0)
        try {
            columnWidth = typedArray.getDimension(R.styleable.AutofitRecyclerView_column_width, 0f).toInt()
        } finally {
            typedArray.recycle()
        }

        init()

    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        if (columnWidth == 0) {
            columnWidth = getResources().getDimensionPixelSize(R.dimen.column_width)
        }
        manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        setLayoutManager(manager)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = Math.max(1, getMeasuredWidth() / columnWidth)
            manager!!.spanCount = spanCount
        }
    }

}
