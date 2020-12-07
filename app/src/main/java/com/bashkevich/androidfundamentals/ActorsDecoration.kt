package com.bashkevich.androidfundamentals

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ActorsDecoration : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = view.context.resources.getDimension(R.dimen.medium_margin).toInt()
    }
}