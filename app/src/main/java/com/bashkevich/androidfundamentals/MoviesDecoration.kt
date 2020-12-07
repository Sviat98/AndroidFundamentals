package com.bashkevich.androidfundamentals

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MoviesDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = view.context.resources.getDimension(R.dimen.fourteen_dp_margin).toInt()
        outRect.right = view.context.resources.getDimension(R.dimen.fourteen_dp_margin).toInt()
        outRect.bottom = view.context.resources.getDimension(R.dimen.ten_dp_margin).toInt()
    }

}