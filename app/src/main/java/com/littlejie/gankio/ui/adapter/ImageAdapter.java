package com.littlejie.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.littlejie.core.ui.BaseImageView;
import com.littlejie.gankio.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by littlejie on 2017/3/11.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> mImageUrlList;
    private List<Integer> mHeightList;

    public void setImageUrlList(List<String> imageUrlList) {
        mImageUrlList = imageUrlList;
        mHeightList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = mImageUrlList == null ? null : mImageUrlList.get(position);

        //随机生成图片高度
        if (mHeightList.size() <= position) {
            mHeightList.add((int) (Math.random() * 300 + 500));
        }
        ViewGroup.LayoutParams lp = holder.mImageView.getLayoutParams();
        lp.height = mHeightList.get(position);
        holder.mImageView.setLayoutParams(lp);

        if (!TextUtils.isEmpty(url)) {
            holder.mImageView.setImage(url);
        } else {
            holder.mImageView.setImage(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mImageUrlList == null ? 0 : mImageUrlList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        BaseImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
