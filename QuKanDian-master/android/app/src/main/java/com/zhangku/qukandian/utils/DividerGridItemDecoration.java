package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yzn on 2016/5/4.
 * 网格布局item间隔
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    /**
     *
     * @param context
     * @param spanCount 列数
     * @param spacing   间距
     * @param includeEdge 是否包含外边
     */
    public DividerGridItemDecoration(Context context, int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = DisplayUtils.dip2px(context, spacing);
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int count = parent.getAdapter().getItemCount();
        int column = position % spanCount;
        int row = position / spanCount;
        leftAndRightSpacing(outRect, column, includeEdge);
        topAndBottomSpacing(outRect, row, position, includeEdge);
    }

    /**
     * 左右边距
     *
     * @param outRect
     * @param column
     */
    private void leftAndRightSpacing(Rect outRect, int column, boolean includeEdge) {
        if (column == 0) {
            if (includeEdge) {
                outRect.left = spacing;
            } else {
                outRect.left = 0;
            }
            outRect.right = 0;
        } else if (column == spanCount - 1) {
            if (includeEdge) {
                outRect.right = spacing;
            } else {
                outRect.right = 0;
            }
            outRect.left = 0;
        } else {
            outRect.left = spacing / 2;
            outRect.right = spacing / 2;
        }
    }

    /**
     * 上下边距
     *
     * @param outRect
     * @param row
     * @param position
     */
    private void topAndBottomSpacing(Rect outRect, int row, int position, boolean includeEdge) {
        if (row == 0) {
            if (includeEdge) {
                outRect.top = spacing;
            } else {
                outRect.top = 0;
            }
            outRect.bottom = spacing;
        } else if (row == position / spanCount) {
            if (includeEdge) {
                outRect.bottom = spacing;
                outRect.top = 0;
            } else {
                outRect.bottom = 0;
                outRect.top = 0;
            }

        } else {
            outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }
}
