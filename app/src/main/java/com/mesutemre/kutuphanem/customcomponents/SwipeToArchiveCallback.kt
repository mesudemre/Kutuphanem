package com.mesutemre.kutuphanem.customcomponents

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import android.util.DisplayMetrics

/**
 * @Author: mesutemre.celenk
 * @Date: 21.08.2021
 */
abstract class SwipeToArchiveCallback(context: Context,direction:Int) :
    ItemTouchHelper.SimpleCallback(0, direction) {

    private var icon = context.getDrawable(R.drawable.ic_baseline_archive_white);
    private var intrinsicWidth = icon?.intrinsicWidth
    private var intrinsicHeight = icon?.intrinsicHeight
    private val background = ColorDrawable();
    private var backgroundColor = context.getColor(R.color.acik_kirmizi);
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
    private val direction = direction;
    private val context = context;

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false;
    }



    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView;
        val itemHeight = itemView.bottom-itemView.top;
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        if(this.direction == ItemTouchHelper.LEFT){
            background.color = backgroundColor
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            background.draw(c);

            val archiveIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2;
            val archiveIconMargin = (itemHeight - intrinsicHeight!!) / 2;
            val archiveIconLeft = itemView.right - archiveIconMargin - intrinsicWidth!!;
            val archiveIconRight = itemView.right - archiveIconMargin;
            val archiveIconBottom = archiveIconTop + intrinsicHeight!!;

            icon?.setBounds(archiveIconLeft, archiveIconTop, archiveIconRight, archiveIconBottom)
            icon?.draw(c)
        }else if(this.direction == ItemTouchHelper.RIGHT){
            background.color = context.getColor(R.color.primaryTextColor);
            background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
            background.draw(c);

            icon = context.getDrawable(R.drawable.ic_baseline_share_24);
            intrinsicHeight = icon?.intrinsicHeight;
            intrinsicWidth = icon?.intrinsicWidth;
            val margin = convertDpToPx(48);
            val shareIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2;
            val shareIconLeft = margin
            val shareIconRight = margin + intrinsicWidth!!;
            val shareIconBottom = shareIconTop + intrinsicHeight!!;
            icon?.setBounds(shareIconLeft, shareIconTop,shareIconRight , shareIconBottom);
            icon?.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private fun convertDpToPx(dp: Int): Int {
        return Math.round(dp * (context.resources.getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT))
            .toInt()
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}