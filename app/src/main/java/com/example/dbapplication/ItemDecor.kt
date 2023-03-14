package com.example.dbapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecor(private val dividerHeight: Int, private val dividerColor: Int) : RecyclerView.ItemDecoration() {
    private val paint = Paint()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = dividerHeight
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        paint.color = WHITE
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}