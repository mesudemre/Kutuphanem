package com.mesutemre.kutuphanem.customcomponents

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingDecoration(
        @Px private val itemSpacing: Int,
        @Px private val edgeSpacing: Int = 0): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.adapter?.itemCount ?: 0
        val position = parent.getChildAdapterPosition(view)
        val leading = if (position == 0) edgeSpacing else itemSpacing
        val trailing = if (position == count - 1) edgeSpacing else 0
        outRect.run {
            if ((parent.layoutManager as? LinearLayoutManager)?.orientation == LinearLayout.VERTICAL) {
                top = leading
                bottom = trailing
            } else {
                left = leading
                right = trailing
            }
        }
    }
}