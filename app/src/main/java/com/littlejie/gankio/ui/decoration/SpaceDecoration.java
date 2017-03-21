package com.littlejie.gankio.ui.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by littlejie on 2017/3/12.
 */

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public SpaceDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int position = parent.getChildAdapterPosition(view);
//        int type = parent.getAdapter().getItemViewType(position);
//        if (type == CategoryAdapter.TYPE_DATE) {
//            outRect.left = mSpace;
//            outRect.top = mSpace;
//            outRect.bottom = -mSpace;
//            outRect.right = mSpace;
//            return;
//        }
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        outRect.top = mSpace;
    }
}
