package com.example.mytestnav.chosetheSeats

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpanItemDecoration(private val spacing:Int,private val type:String):ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spanCount=(parent.layoutManager as GridLayoutManager).spanCount
        when(type){
            "Normal"->{
                val halfSpacing=spacing/4
                val position=parent.getChildAdapterPosition(view)
                when(position % spanCount){
                    0->{
                        outRect.left=halfSpacing
                        outRect.right=0
                    }
                    1->{
                        outRect.left=0
                        outRect.right=spacing+20
                    }
                    2->{
                        outRect.left=spacing
                        outRect.right=halfSpacing
                    }
                    else->{
                        outRect.left=0
                        outRect.right=0
                    }
                }
                outRect.top=0
                outRect.bottom=0
            }
            "Vip"->{
                val halfSpacing=spacing/4
                val position=parent.getChildAdapterPosition(view)
                when(position % spanCount){
                    0->{
                        outRect.left=3*halfSpacing
                        outRect.right=0
                    }
                    1->{
                        outRect.left=-50
                        outRect.right=spacing+80

                    }
                    2->{
                        outRect.left=2*spacing-10
                        outRect.right=0
                    }
                    else->{
                        outRect.left=0
                        outRect.right=0
                    }
                }
                outRect.top=0
                outRect.bottom=0
            }
        }


    }
}